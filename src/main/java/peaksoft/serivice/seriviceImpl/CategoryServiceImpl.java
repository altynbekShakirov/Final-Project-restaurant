package peaksoft.serivice.seriviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Category;
import peaksoft.repository.CategoryRepository;
import peaksoft.serivice.CategoryService;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Successfully saved!!!").build();
    }

    @Override
    public CategoryResponse getByCategoryId(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("This id:"+id+" does not exist"));
        return new CategoryResponse(category.getName());
    }

    @Override
    public Set<CategoryResponse> getAllCategories() {
        return categoryRepository.getAllCategory();
    }

    @Override
    public SimpleResponse updateCategory(Long id,CategoryRequest categoryRequest) {
        Category category =categoryRepository.findById(id).orElseThrow();
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Successfully updated!!!").build();

    }

    @Override
    public SimpleResponse deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        return new SimpleResponse(HttpStatus.OK,"Successfully deleted!!");
    }
}
