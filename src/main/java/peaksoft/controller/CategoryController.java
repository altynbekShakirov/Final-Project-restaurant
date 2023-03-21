package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.serivice.CategoryService;

import java.util.Set;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse saveCategory(@RequestBody CategoryRequest categoryRequest){
        return categoryService.saveCategory(categoryRequest);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER','CHEF')")
    public CategoryResponse getById(@PathVariable Long id){
        return categoryService.getByCategoryId(id);
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER','CHEF')")
    public Set<CategoryResponse> getAllCategory(){
        return categoryService.getAllCategories();
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse updateCategory(@PathVariable Long id,@RequestBody CategoryRequest categoryRequest){
        return categoryService.updateCategory(id,categoryRequest);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }

}
