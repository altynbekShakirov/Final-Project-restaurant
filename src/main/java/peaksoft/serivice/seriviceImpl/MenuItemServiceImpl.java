package peaksoft.serivice.seriviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.MenuItemResponseSearch;
import peaksoft.dto.response.PaginationResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.MenuItem;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.BadRequestException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.serivice.MenuItemService;


import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository repository;
    private final SubcategoryRepository subcategoryRepository;


    @Autowired
    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, RestaurantRepository repository, SubcategoryRepository subcategoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.repository = repository;
        this.subcategoryRepository = subcategoryRepository;

    }

    @Override
    public SimpleResponse saveMenuItem(MenuItemRequest menuitemRequest) {
        if (menuitemRequest.price().intValue() < 0) {
            throw new BadRequestException("Price can't be negative number!");
        }
        if (menuItemRepository.existsByName(menuitemRequest.name())) {
            throw new AlreadyExistException("Menu item name: " + menuitemRequest.name() + " already exist!!!");
        }
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuitemRequest.name());
        menuItem.setDescription(menuitemRequest.description());
        menuItem.setPrice(menuitemRequest.price());
        menuItem.setImage(menuitemRequest.image());
        menuItem.setVegetarian(menuitemRequest.isVegetarian());
        menuItem.setRestaurant(repository.findRestaurants().orElseThrow(() -> new NotFoundException("Restaurant is null")));
        menuItem.setSubcategory(subcategoryRepository.findByName(menuitemRequest.subcategory()).orElseThrow(() -> new NotFoundException("This name: " + menuitemRequest.subcategory() + " does not exist!!")));
        menuItemRepository.save(menuItem);
        return new SimpleResponse(HttpStatus.OK, "Successfully saved!!");
    }

    @Override
    public MenuItemResponse getById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() -> new NotFoundException("This id:" + id + " does not exist"));
        return new MenuItemResponse(menuItem.getName(), menuItem.getImage(), menuItem.getPrice(), menuItem.getDescription(), menuItem.isVegetarian());
    }

    @Override
    public Set<MenuItemResponse> getAllMenu() {
        Set<MenuItem> all = menuItemRepository.getAll();
        Set<MenuItemResponse> responses = new LinkedHashSet<>();
        for (MenuItem menuItem : all) {
            if (menuItem.getStopList() != null && !menuItem.getStopList().getDate().equals(LocalDate.now())) {
                responses.add(new MenuItemResponse(menuItem.getName(), menuItem.getImage(), menuItem.getPrice(), menuItem.getDescription(), menuItem.isVegetarian()));
            } else if (menuItem.getStopList() == null) {
                responses.add(new MenuItemResponse(menuItem.getName(), menuItem.getImage(), menuItem.getPrice(), menuItem.getDescription(), menuItem.isVegetarian()));
            }
        }
        return responses;
    }

    @Override
    public SimpleResponse updateMenuItem(Long id, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() -> new NotFoundException("This id:" + id + " does not exist"));
        if (menuItemRequest.price().intValue() < 0) {
            throw new BadRequestException("Price can't be negative number!");
        }
        if (menuItemRepository.existsByName(menuItemRequest.name())) {
            throw new AlreadyExistException("Menu item name: " + menuItemRequest.name() + " already exist!!!");
        }
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setName(menuItemRequest.name());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        if (!subcategoryRepository.existsByName(menuItemRequest.subcategory())) {
            throw new NotFoundException("This name: " + menuItemRequest.subcategory() + " does not exist!!");
        } else {
            menuItem.setSubcategory(subcategoryRepository.findByName(menuItemRequest.subcategory()).orElseThrow(() -> new NotFoundException("This name: " + menuItemRequest.subcategory() + "does not exist!!!")));


        }
        menuItem.setRestaurant(repository.findRestaurant());
        menuItemRepository.save(menuItem);
        return new SimpleResponse(HttpStatus.OK, "Successfully saved!!");

    }

    @Override
    public SimpleResponse deleteMenu(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() -> new NotFoundException("This id: " + id + " does not exist!!"));
        menuItemRepository.delete(menuItem);
        return new SimpleResponse(HttpStatus.OK, "Successfully deleted!!!");
    }

    @Override
    public Set<MenuItemResponse> sort(String ascDesc) {
        switch (ascDesc.toLowerCase()) {
            case "asc" -> {
                return menuItemRepository.ascSort();
            }
            case "desc" -> {
                return menuItemRepository.descSort();
            }
            default -> throw new NotFoundException("Select ascending or descending!!");
        }
    }

    @Override
    public Set<MenuItemResponse> filterIsVegeterian() {
        return getAllMenu().stream().filter(MenuItemResponse::isVegetarian).collect(Collectors.toSet());
    }

    @Override
    public Set<MenuItemResponseSearch> search(String search) {
        return menuItemRepository.searchBySubcategories(search);
    }

    @Override
    public PaginationResponse getMenuItemPage(int currentPage, int size) {
        Pageable pageable = PageRequest.of(currentPage -1, size);
        Page<MenuItem> all = menuItemRepository.findAll(pageable);
        return PaginationResponse.builder().list(all.getContent()).currentPage(pageable.getPageNumber()+1)
                .pageSize(all.getTotalPages()).build();

    }


}
