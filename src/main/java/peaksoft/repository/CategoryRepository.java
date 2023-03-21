package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.entity.Category;

import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select new peaksoft.dto.response.CategoryResponse(c.name)from Category  c")
    Set<CategoryResponse>getAllCategory ();
    @Query("from Category c where c.name=:name")
    Category findByName(String name);
//    @Query("select true from Category  c where  c.name=:name and c.name!=null ")
//    Boolean existsByName(String name);
}