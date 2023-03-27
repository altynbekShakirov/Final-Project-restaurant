package peaksoft.serivice.seriviceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.SubcategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubcategoryResponse;
import peaksoft.dto.response.pageResponse.PageSubCategoriesResponse;
import peaksoft.entity.Subcategory;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.serivice.SubcategoryService;

import java.util.Set;

@Service

public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public SimpleResponse saveSubcategory(SubcategoryRequest subcategoryRequest) {
        Subcategory subcategory = new Subcategory();
        if (subcategoryRepository.existsByName(subcategoryRequest.name())) {
            throw new AlreadyExistException("Subcategory name already exist!!");
        }
        subcategory.setName(subcategoryRequest.name());
        subcategory.setCategory(categoryRepository.findByName(subcategoryRequest.categoryName()).orElseThrow(() -> {
            throw new NotFoundException("Category with name - " + subcategoryRequest.categoryName() + " is not found!");
        }));

        subcategoryRepository.save(subcategory);
        return new SimpleResponse(HttpStatus.OK, "Successfully saved!");
    }

    @Override
    public SubcategoryResponse getById(Long id) {
        Subcategory subcategory = subcategoryRepository.findById(id).orElseThrow(() -> new NotFoundException("This id:" + id + " does not exist"));
        return new SubcategoryResponse(subcategory.getName());
    }

    @Override
    public Set<SubcategoryResponse> getAllSubcategories() {
        return subcategoryRepository.getAllSubcategories();
    }

    @Override
    public SimpleResponse updateSubcategory(Long id, SubcategoryRequest subcategoryRequest) {
        Subcategory subcategory = subcategoryRepository.findById(id).orElseThrow(() -> new NotFoundException("This id:" + id + " does not exist"));
        if (subcategoryRepository.existsByName(subcategoryRequest.name())) {
            throw new AlreadyExistException("Subcategory name already exist!!");
        }
        subcategory.setName(subcategoryRequest.name());
        subcategory.setCategory(categoryRepository.findByName(subcategoryRequest.categoryName()).orElseThrow(() -> {
            throw new NotFoundException("Category with name - " + subcategoryRequest.categoryName() + " is not found!");
        }));
        subcategoryRepository.save(subcategory);
        return new SimpleResponse(HttpStatus.OK, "Successfully updated!!");
    }

    @Override
    public SimpleResponse deleteSubcategory(Long id) {
        Subcategory subcategory = subcategoryRepository.findById(id).orElseThrow(() -> new NotFoundException("This id:" + id + " does not exist"));
        subcategoryRepository.delete(subcategory);
        return new SimpleResponse(HttpStatus.OK, "Successfully deleted!!");
    }

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
    @Override
    public PageSubCategoriesResponse getSubcategoryPage(int currentPage, int size) {
        Pageable pageable = PageRequest.of(currentPage -1, size);
        Page<Subcategory> all = subcategoryRepository.findAll(pageable);
        return PageSubCategoriesResponse.builder().subcategories(all.getContent()).currentPage(pageable.getPageNumber()+1)
                .size(all.getTotalPages()).build();

    }

}
