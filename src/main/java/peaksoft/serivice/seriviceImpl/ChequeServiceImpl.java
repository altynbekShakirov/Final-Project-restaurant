package peaksoft.serivice.seriviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.ChequeOfRestaurantAmountDayRequest;
import peaksoft.dto.request.ChequeOneDayTotalAmountRequest;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.request.ChequeUpdateRequest;
import peaksoft.dto.response.*;
import peaksoft.dto.response.pageResponse.PageChequeResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.entity.enums.Role;
import peaksoft.exception.BadCredentialException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.*;
import peaksoft.serivice.ChequeService;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
@Slf4j
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final StopListRepository stopListRepository;
    private final RestaurantRepository restaurantRepository;

    public ChequeServiceImpl(ChequeRepository chequeRepository, UserRepository userRepository, MenuItemRepository menuItemRepository, StopListRepository stopListRepository, RestaurantRepository restaurantRepository) {
        this.chequeRepository = chequeRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
        this.stopListRepository = stopListRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public SimpleResponse saveCheque(ChequeRequest chequeRequest) {
        Cheque cheque = new Cheque();
        cheque.setCreateAt(LocalDateTime.now().toLocalDate());
        cheque.setUser(userRepository.findByEmail(chequeRequest.email()).orElseThrow(() -> new NotFoundException("This email:" + chequeRequest.email() + " does not exist")));
        for (String food : chequeRequest.foods()) {
            MenuItem byName = menuItemRepository.findByName(food).orElseThrow(()->new NotFoundException("This name:"+food+"does not exist!!"));
            if (stopListRepository.counts(LocalDate.now(), food) > 0) {
                throw new BadCredentialException("This dish is on the Stop list!!!");
            }
            cheque.getMenuItems().add(byName);
        }
        chequeRepository.save(cheque);
        return new SimpleResponse(HttpStatus.OK, "Successfully saved!!!");
    }

    @Override
    public Set<ChequeResponse> getAllCheque() {
        return chequeRepository.findAllCheque();
    }


    @Override
    public SimpleResponse updateCheque(Long id, ChequeUpdateRequest chequeRequest) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() -> new NotFoundException("This id:" + id + " does not exist!!"));
        Set<MenuItem> menuItems = new LinkedHashSet<>();
        for (String food : chequeRequest.menuItemSet()) {
            menuItems.add(menuItemRepository.findByName(food).orElseThrow(()->new NotFoundException("This name: "+food+" doest not exist!!")));
        }
        cheque.setMenuItems(menuItems);
        chequeRepository.save(cheque);
        return new SimpleResponse(HttpStatus.OK, "Successfully updated!!!");
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() -> new NotFoundException("This id: " + id + " does not exist!!!"));
        chequeRepository.delete(cheque);
        return new SimpleResponse(HttpStatus.OK, "Successfully deleted!!");
    }

    @Override
    public ChequeFinalResponse findById(Long id) {


        Cheque cheque;
        cheque = chequeRepository.findById(id).orElseThrow();
        User employee = cheque.getUser();

        Set<MenuItemResponse> menuItems = convert(cheque.getMenuItems());
        int totalPrice = 0;
        for (MenuItemResponse menu : menuItems) {
            totalPrice = totalPrice + menu.price().intValue();
        }
        int service = totalPrice + (totalPrice * employee.getRestaurant().getService() / 100);


        return ChequeFinalResponse.builder().
                fullName(cheque.getUser()
                        .getFirstName()
                        .concat(" ")
                        .concat(cheque.getUser()
                                .getLastName()))
                .service(totalPrice * employee.getRestaurant().getService() / 100)
                .price(totalPrice)
                .globalTotal(service)
                .items(convert(cheque.getMenuItems())).build();


    }

    @Override
    public ChequeOneDayTotalAmountResponse findAllChequesOneDayTotalAmount(ChequeOneDayTotalAmountRequest request) {
        System.out.println(userRepository.findByIdQuery(request.waiterId()));
        User user = userRepository.findById(request.waiterId()).orElseThrow(
                () -> new NotFoundException("User with id : " + request.waiterId() + "is not found!"));
        int chequeCount = 0;
        int totalAmount = 0;
        if (user.getRole().equals(Role.WAITER)) {
            for (Cheque che : user.getCheques()) {
                if (che.getCreateAt().equals(request.date())) {
                    int service = che.getPriceAverage() * user.getRestaurant().getService() / 100;
                    totalAmount = service + che.getPriceAverage();
                    ++chequeCount;
                }
            }
        }
        return ChequeOneDayTotalAmountResponse.builder().numberOfCheques(chequeCount).totalAmount(BigDecimal.valueOf(totalAmount)).waiterFullName(user.getFirstName() + " " + user.getLastName()).build();

    }

    @Override
    public ChequeOfRestaurantAmountDayResponse countRestGrantTotalForDay(ChequeOfRestaurantAmountDayRequest chequeOfRestaurantAmountDayRequest) {
        Restaurant restaurant = restaurantRepository.findRestaurant();
        int numberOfWaiters = 0;
        int numberOfCheque = 0;
        int totalAmount = 0;
        for (User userWaiter : restaurant.getUsers()) {
            if (userWaiter.getRole().equals(Role.WAITER)) {
                for (Cheque waiterCh : userWaiter.getCheques()) {
                    if (waiterCh.getCreateAt() == chequeOfRestaurantAmountDayRequest.date()) {
                        var restaurantService = waiterCh.getPriceAverage() * restaurant.getService() / 100;
                        totalAmount = restaurantService + waiterCh.getPriceAverage();
                        numberOfCheque++;
                    }

                }
                numberOfWaiters++;
            }
        }
        return ChequeOfRestaurantAmountDayResponse.builder().numberOfCheque(numberOfCheque).numberOfWaiters(numberOfWaiters).totalAmount(totalAmount).build();
    }

    private MenuItemResponse convert(MenuItem menuItem) {
        return MenuItemResponse.builder().description(menuItem.getDescription()).image(menuItem.getImage()).price(menuItem.getPrice()).isVegetarian(menuItem.isVegetarian()).name(menuItem.getName()).build();
    }

    private Set<MenuItemResponse> convert(Set<MenuItem> menuItems) {
        Set<MenuItemResponse> menuItemResponses = new LinkedHashSet<>();
        for (MenuItem menuItem : menuItems) {
            menuItemResponses.add(convert(menuItem));
        }
        return menuItemResponses;
    }
    @Override
    public PageChequeResponse getPageCheque(int page,int size){
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Cheque> all = chequeRepository.findAll(pageable);


        return PageChequeResponse.builder().cheques(all.getContent()).currentPage(pageable.getPageNumber()+1).size(all.getTotalPages()).build();
    }
}
