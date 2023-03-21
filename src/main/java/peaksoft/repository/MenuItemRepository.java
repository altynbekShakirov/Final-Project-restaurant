package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SubcategoryResponse;
import peaksoft.entity.MenuItem;

import java.util.Set;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("select new peaksoft.dto.response.MenuItemResponse(m.name,m.image,m.price,m.description,m.isVegetarian)from MenuItem  m")
    Set<MenuItemResponse>findAllMenu();
    @Query("select new peaksoft.dto.response.MenuItemResponse(m.name,m.image,m.price,m.description,m.isVegetarian)from MenuItem  m where m.id=:id")
    MenuItemResponse findByMenuId(Long id);

    @Query("select new peaksoft.dto.response.MenuItemResponse(s.name,s.image,s.price,s.description,s.isVegetarian)from MenuItem s order by s.price")
    Set<MenuItemResponse> ascSort();
    @Query("select new peaksoft.dto.response.MenuItemResponse(s.name,s.image,s.price,s.description,s.isVegetarian)from MenuItem s order by s.price desc ")
    Set<MenuItemResponse> descSort();
    MenuItem findByName(String name);
    @Query("select mi from MenuItem mi join mi.cheques c WHERE c.id = :chequeId")
    Set<MenuItem> findAllByChequeId(Long chequeId);

}