package peaksoft.serivice;

import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.MenuItemResponseSearch;
import peaksoft.dto.response.SimpleResponse;

import java.util.Set;

public interface MenuItemService {
    SimpleResponse saveMenuItem(MenuItemRequest menuitemRequest);
    MenuItemResponse getById(Long id);
    Set<MenuItemResponse> getAllMenu();
    SimpleResponse updateMenuItem(Long id,MenuItemRequest menuItemRequest);
    SimpleResponse deleteMenu(Long id);
    Set<MenuItemResponse> sort(String askDesc);
    Set <MenuItemResponse> filterIsVegeterian ();
    Set<MenuItemResponseSearch>search(String search);



}
