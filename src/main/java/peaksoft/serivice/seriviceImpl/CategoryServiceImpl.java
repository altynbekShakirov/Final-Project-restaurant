package peaksoft.serivice.seriviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.pageResponse.PageCategoryResponse;
import peaksoft.entity.Category;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.serivice.CategoryService;

import java.util.Set;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) throws AlreadyExistException {
        Category category = new Category();
        if (categoryRepository.existsByName(categoryRequest.name())) {
            throw new AlreadyExistException("Category already exist!!");
        }
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Successfully saved!!!").build();
    }

    @Override
    public CategoryResponse getByCategoryId(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("This id:" + id + " does not exist"));
        return new CategoryResponse(category.getName());
    }

    @Override
    public Set<CategoryResponse> getAllCategories() {
        return categoryRepository.getAllCategory();
    }

    @Override
    public SimpleResponse updateCategory(Long id, CategoryRequest categoryRequest) throws AlreadyExistException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("This id:" + id + " does not exist"));
        if (categoryRepository.existsByName(categoryRequest.name())) {
            throw new AlreadyExistException("Category already exist!!");
        }
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Successfully updated!!!").build();

    }

    @Override
    public SimpleResponse deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("This id:" + id + " does not exist"));
        categoryRepository.delete(category);
        return new SimpleResponse(HttpStatus.OK, "Successfully deleted!!");
    }

    @Override
    public PageCategoryResponse getPageCategory(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Category> all = categoryRepository.findAll(pageable);
        return PageCategoryResponse.builder().categories(all.getContent()).currentPage(pageable.getPageNumber() + 1).size(all.getTotalPages()).build();
    }
}
