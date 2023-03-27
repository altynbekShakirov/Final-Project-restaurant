package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.entity.Category;

import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select new peaksoft.dto.response.CategoryResponse(c.name)from Category  c")
    Set<CategoryResponse>getAllCategory ();
    @Query("select c from Category c where c.name = :name")
    Optional<Category> findByName(String name);
    Boolean existsByName(String name);

    @Override
    Page<Category> findAll(Pageable pageable);
}