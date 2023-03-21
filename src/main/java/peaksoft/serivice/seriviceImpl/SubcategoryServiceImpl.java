package peaksoft.serivice.seriviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.request.SubcategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubcategoryResponse;
import peaksoft.entity.Category;
import peaksoft.entity.Subcategory;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.serivice.CategoryService;
import peaksoft.serivice.SubcategoryService;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryService categoryService;


    @Override
    public SimpleResponse saveSubcategory(SubcategoryRequest subcategoryRequest) {
        Subcategory subcategory = new Subcategory();
        subcategory.setName(subcategoryRequest.name());
        Category category = new Category();
        category.setName(subcategoryRequest.name());
        subcategory.setCategory(category);
        subcategoryRepository.save(subcategory);
        return new SimpleResponse(HttpStatus.OK, "Successfully saved!");
    }

    @Override
    public SubcategoryResponse getById(Long id) {
        Subcategory subcategory = subcategoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("This id:"+id+" does not exist"));
        return new SubcategoryResponse(subcategory.getName());
    }

    @Override
    public Set<SubcategoryResponse> getAllSubcategories() {
        return subcategoryRepository.getAllSubcategories();
    }

    @Override
    public SimpleResponse updateSubcategory(Long id, SubcategoryRequest subcategoryRequest) {
        Subcategory subcategory = subcategoryRepository.findById(id).orElseThrow(()->new NoSuchElementException("This id:"+id+" does not exist"));
        subcategory.setName(subcategoryRequest.name());
        Category category = new Category();
        category.setName(subcategoryRequest.name());
        subcategory.setCategory(category);
        subcategoryRepository.save(subcategory);
        return new SimpleResponse(HttpStatus.OK, "Successfully updated!!");
    }
//
//
    @Override
    public SimpleResponse deleteSubcategory(Long id) {
        subcategoryRepository.deleteById(id);
        return new SimpleResponse(HttpStatus.OK, "Successfully deleted!!");
    }
//
    @Override
    public Set<SubcategoryResponse> sort(String ascDesc) {
        try {

            switch (ascDesc.toLowerCase()) {
                case "asc" -> {

                    return subcategoryRepository.ascSort();
                }
                case "desc" -> {

                    return subcategoryRepository.descSort();

                }
                default -> System.err.println("Select ascending or descending!!");

            }


        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Set<SubcategoryResponse> groupBy() {
        return subcategoryRepository.groupBy();
    }
}
