package peaksoft.serivice;

import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.pageResponse.PageCategoryResponse;
import peaksoft.exception.AlreadyExistException;

import java.util.Set;

public interface CategoryService {
    SimpleResponse saveCategory(CategoryRequest categoryRequest) throws AlreadyExistException;

    CategoryResponse getByCategoryId(Long id);

    Set<CategoryResponse> getAllCategories();

    SimpleResponse updateCategory(Long id, CategoryRequest categoryRequest) throws AlreadyExistException;

    SimpleResponse deleteCategory(Long id);
    PageCategoryResponse getPageCategory(int currentPage,int size);

}
