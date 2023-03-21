package peaksoft.serivice.seriviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.request.ChequeUpdateRequest;
import peaksoft.dto.response.ChequeFinalResponse;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.User;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.UserRepository;
import peaksoft.serivice.ChequeService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public SimpleResponse saveCheque(ChequeRequest chequeRequest) {
        Cheque cheque = new Cheque();
        cheque.setCreateAt(LocalDateTime.now().toLocalDate());
        cheque.setUser(userRepository.findByEmail(chequeRequest.email()).orElseThrow(() -> new NoSuchElementException("This email:"+chequeRequest.email()+" does not exist")));
        for (String food : chequeRequest.foods()) {
            cheque.getMenuItems().add(menuItemRepository.findByName(food));
        }
        chequeRepository.save(cheque);
        return new SimpleResponse(HttpStatus.OK, "Successfully saved!!!");
    }

    @Override
    public Set<ChequeResponse> getAllCheque() {
        return chequeRepository.findAllCheque();
    }

//    @Override
//    public ChequeFinalResponse getById(Long id) {
//        Cheque cheque = chequeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Myndai id jok!!!"));
//        int price = 0;
//        Set<MenuItem> allByChequeId = menuItemRepository.findAllByChequeId(id);
//        for (MenuItem menuItem : allByChequeId) {
//            price += menuItem.getPrice().intValue();
//
//        }
//        int globalTotal = price + (price * cheque.getUser().getRestaurant().getService() / 100);
//
//        return new ChequeFinalResponse(cheque.getUser().getFirstName() + " " + cheque
//                .getUser().getLastName(),
//                cheque.getMenuItems(), price
//                , cheque.getUser().getRestaurant().getService(), globalTotal
//        );
//    }

    @Override
    public SimpleResponse updateCheque(Long id, ChequeUpdateRequest chequeRequest) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("This id:"+id+" does not exist!!"));
        Set<MenuItem>menuItems = new LinkedHashSet<>();
        for (String food : chequeRequest.menuItemSet()) {
           menuItems.add(menuItemRepository.findByName(food));
        }
        cheque.setMenuItems(menuItems);
        chequeRepository.save(cheque);
        return new SimpleResponse(HttpStatus.OK, "Successfully updated!!!");
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        chequeRepository.deleteById(id);
        return new SimpleResponse(HttpStatus.OK, "Successfully deleted!!");
    }

    @Override
    public ChequeFinalResponse findById(Long id) {


        Cheque cheque;
        cheque = chequeRepository.findById(id).orElseThrow();
        User employee = cheque.getUser();

        Set<MenuItemResponse> menuItems = convert(cheque.getMenuItems());
        int totalPrice = 0;
        int grandTotal = 0;
        for (MenuItemResponse menu:menuItems) {
            totalPrice = totalPrice + menu.price().intValue();
        }
        grandTotal = totalPrice + (totalPrice * employee.getRestaurant().getService() / 100);


        return ChequeFinalResponse.builder().
                fullName(cheque.getUser()
                        .getFirstName()
                        .concat(" ")
                        .concat(cheque.getUser()
                                .getLastName()))
                .service(cheque.getUser().getRestaurant().getService())
                .price(BigDecimal.valueOf(totalPrice/cheque.getMenuItems().size()).intValue())
                .globalTotal(grandTotal)
                .items(convert(cheque.getMenuItems())).build();


    }

    private MenuItemResponse convert(MenuItem menuItem) {
        return MenuItemResponse.builder().description(menuItem.getDescription()).image(menuItem.getImage()).price(menuItem.getPrice()).isVegetarian(menuItem.isVegetarian()).name(menuItem.getName()).build();
    }

    private Set<MenuItemResponse>convert(Set<MenuItem>menuItems){
        Set<MenuItemResponse>menuItemResponses=new LinkedHashSet<>();
        for (MenuItem menuItem : menuItems) {
            menuItemResponses.add(convert(menuItem));
        }
        return menuItemResponses;
    }
}
