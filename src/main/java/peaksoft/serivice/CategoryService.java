package peaksoft.serivice;

import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.Set;

public interface CategoryService {
    SimpleResponse saveCategory(CategoryRequest categoryRequest);
    CategoryResponse getByCategoryId(Long id);
    Set<CategoryResponse> getAllCategories();
    SimpleResponse updateCategory(Long id,CategoryRequest categoryRequest);
    SimpleResponse deleteCategory(Long id);

}
