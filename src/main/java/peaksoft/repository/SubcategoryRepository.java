package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.SubcategoryResponse;
import peaksoft.entity.Subcategory;

import java.util.Optional;
import java.util.Set;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
    @Query("select new peaksoft.dto.response.SubcategoryResponse(s.name)from Subcategory s")
    Set<SubcategoryResponse> getAllSubcategories();

    @Query("select new peaksoft.dto.response.SubcategoryResponse(s.name)from Subcategory s order by s.name")
    Set<SubcategoryResponse> ascSort();

    @Query("select new peaksoft.dto.response.SubcategoryResponse(s.name)from Subcategory s  order by s.name desc ")
    Set<SubcategoryResponse> descSort();

    @Query("select new peaksoft.dto.response.SubcategoryResponse(s.name)from Subcategory s  group by  s.id ")
    Set<SubcategoryResponse> groupBy();

    @Query("select  s from Subcategory  s where s.name=:name")
    Optional<Subcategory> findByName(String name);

    Boolean existsByName(String name);

    @Override
    Page<Subcategory> findAll(Pageable pageable);
}