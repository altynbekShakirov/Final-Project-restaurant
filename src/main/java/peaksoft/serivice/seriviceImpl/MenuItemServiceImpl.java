package peaksoft.serivice.seriviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.MenuItemResponseSearch;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Subcategory;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.serivice.MenuItemService;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private  final RestaurantRepository repository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public SimpleResponse saveMenuItem(MenuItemRequest menuitemRequest) {
        if (menuitemRequest.price().intValue() < 0) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Price can't be negative number!")
                    .build();
        }
        MenuItem  menuItem = new MenuItem();
        menuItem.setName(menuitemRequest.name());
        menuItem.setDescription(menuitemRequest.description());
        menuItem.setPrice(menuitemRequest.price());
        menuItem.setImage(menuitemRequest.image());
        menuItem.setVegetarian(menuitemRequest.isVegetarian());
        menuItem.setRestaurant(repository.findRestaurant());
        if (!subcategoryRepository.existsByName(menuitemRequest.subcategory())){
            throw new NoSuchElementException("This name: "+menuitemRequest.subcategory()+" does not exist!!");
        }else {
            menuItem.setSubcategory(subcategoryRepository.findByName(menuitemRequest.subcategory()));


        }
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
        if (menuItemRequest.price().intValue() < 0) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Price can't be negative number!")
                    .build();
        }
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setName(menuItemRequest.name());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        if (!subcategoryRepository.existsByName(menuItemRequest.subcategory())){
            throw new NoSuchElementException("This name: "+menuItemRequest.subcategory()+" does not exist!!");
        }else {
            menuItem.setSubcategory(subcategoryRepository.findByName(menuItemRequest.subcategory()));


        }        menuItem.setRestaurant(repository.findRestaurant());
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

    @Override
    public Set<MenuItemResponseSearch> search(String search) {
//        Set<MenuItemResponseSearch>searchSet=new LinkedHashSet<>();
//
//        searchSet.addAll(menuItemRepository.searchBySubcategories(search));

        return menuItemRepository.searchBySubcategories(search);
    }


}
