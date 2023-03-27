package peaksoft.serivice;

import peaksoft.dto.request.SubcategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubcategoryResponse;
import peaksoft.dto.response.pageResponse.PageSubCategoriesResponse;

import java.util.Set;


public interface SubcategoryService {
    SimpleResponse saveSubcategory(SubcategoryRequest subcategoryRequest);

    SubcategoryResponse getById(Long id);

    Set<SubcategoryResponse> getAllSubcategories();

    SimpleResponse updateSubcategory(Long id, SubcategoryRequest subcategoryRequest);

    SimpleResponse deleteSubcategory(Long id);

    Set<SubcategoryResponse> sort(String ascDesc);

    Set<SubcategoryResponse> groupBy();
    PageSubCategoriesResponse getSubcategoryPage(int currentPage, int size);

}
