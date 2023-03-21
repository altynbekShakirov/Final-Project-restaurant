package peaksoft.serivice.seriviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.MenuItem;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.serivice.MenuItemService;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private  final RestaurantRepository repository;

    @Override
    public SimpleResponse saveMenuItem(MenuItemRequest menuitemRequest) {
        MenuItem  menuItem = new MenuItem();
        menuItem.setName(menuitemRequest.name());
        menuItem.setDescription(menuitemRequest.description());
        menuItem.setPrice(menuitemRequest.price());
        menuItem.setImage(menuitemRequest.image());
        menuItem.setVegetarian(menuitemRequest.isVegetarian());
        menuItem.setRestaurant(repository.findRestaurant());
        menuItemRepository.save(menuItem);
        return new SimpleResponse(HttpStatus.OK,"Successfully saved!!");
    }

    @Override
    public MenuItemResponse getById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() -> new NoSuchElementException("This id:"+id+" does not exist"));
        return new MenuItemResponse(menuItem.getName(), menuItem.getImage(), menuItem.getPrice(), menuItem.getDescription(), menuItem.isVegetarian());
    }

    @Override
    public Set<MenuItemResponse> getAllMenu() {
        return menuItemRepository.findAllMenu();
    }

    @Override
    public SimpleResponse updateMenuItem(Long id, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() -> new NoSuchElementException("This id:"+id+" does not exist"));
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setName(menuItemRequest.name());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        menuItem.setRestaurant(repository.findRestaurant());
        menuItemRepository.save(menuItem);
        return new SimpleResponse(HttpStatus.OK,"Successfully saved!!");

    }

    @Override
    public SimpleResponse deleteMenu(Long id) {
        menuItemRepository.deleteById(id);
        return new SimpleResponse(HttpStatus.OK,"Successfully deleted!!!");
    }

    @Override
    public Set<MenuItemResponse> sort(String ascDesc) {
        try  {
            switch (ascDesc.toLowerCase()) {
                case "asc" -> {
                    return menuItemRepository.ascSort();
                }
                case "desc" -> {
                    return menuItemRepository.descSort();
                }
                default -> System.err.println("Select ascending or descending!!");


            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Set<MenuItemResponse> filterIsVegeterian() {
        return menuItemRepository.findAllMenu().stream().filter(MenuItemResponse::isVegetarian).collect(Collectors.toSet());
    }



}
