package peaksoft.serivice.seriviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Category;
import peaksoft.entity.Subcategory;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.serivice.CategoryService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        for (String s : categoryRequest.subcategory()) {
            category.getSubcategories().add(subcategoryRepository.findByName(s));

        }
        categoryRepository.save(category);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Successfully saved!!!").build();
    }

    @Override
    public CategoryResponse getByCategoryId(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("This id:" + id + " does not exist"));
        return new CategoryResponse(category.getName());
    }

    @Override
    public Set<CategoryResponse> getAllCategories() {
        return categoryRepository.getAllCategory();
    }

    @Override
    public SimpleResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(categoryRequest.name());
        Set<Subcategory> subcategories = new LinkedHashSet<>();
        for (String s : categoryRequest.subcategory()) {
            subcategories.add(subcategoryRepository.findByName(s));
        }
        category.setSubcategories(subcategories);
        categoryRepository.save(category);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Successfully updated!!!").build();

    }

    @Override
    public SimpleResponse deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        return new SimpleResponse(HttpStatus.OK, "Successfully deleted!!");
    }
}
