package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.MenuItemResponseSearch;
import peaksoft.entity.MenuItem;

import java.util.Optional;
import java.util.Set;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    @Query("select new peaksoft.dto.response.MenuItemResponse(s.name,s.image,s.price,s.description,s.isVegetarian)from MenuItem s order by s.price")
    Set<MenuItemResponse> ascSort();

    @Query("select new peaksoft.dto.response.MenuItemResponse(s.name,s.image,s.price,s.description,s.isVegetarian)from MenuItem s order by s.price desc ")
    Set<MenuItemResponse> descSort();

    Optional<MenuItem> findByName(String name);

    Boolean existsByName(String name);

    @Query("select m from MenuItem m")
    Set<MenuItem> getAll();

    @Query("select mi from MenuItem mi join mi.cheques c WHERE c.id = :chequeId")
    Set<MenuItem> findAllByChequeId(Long chequeId);

    @Query("SELECT new peaksoft.dto.response.MenuItemResponseSearch(c.name,s.name,m.name,m.price) FROM MenuItem  m join  m.subcategory s join s.category c where m.name ilike concat('%',:search,'%') or s.name ilike concat('%',:search,'%') or c.name ILIKE concat('%',:search,'%')")
    Set<MenuItemResponseSearch> searchBySubcategories(String search);

    @Override
    Page<MenuItem> findAll(Pageable pageable);
}
