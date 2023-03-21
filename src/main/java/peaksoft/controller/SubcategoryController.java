package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.SubcategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubcategoryResponse;
import peaksoft.serivice.SubcategoryService;

import java.util.Set;

@RestController
@RequestMapping("/api/subcategories")
@RequiredArgsConstructor
public class SubcategoryController {
    private final SubcategoryService subcategoryService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse saveSubcategory(@RequestBody SubcategoryRequest subcategoryRequest) {
        return subcategoryService.saveSubcategory(subcategoryRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public SubcategoryResponse getById(@PathVariable Long id) {
        return subcategoryService.getById(id);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public Set<SubcategoryResponse> getAllSubcategory(){
        return subcategoryService.getAllSubcategories();
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse updateSubcategory(@PathVariable Long id ,@RequestBody SubcategoryRequest subcategoryRequest){
        return subcategoryService.updateSubcategory(id,subcategoryRequest);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteSubcategory(@PathVariable Long id){
        return subcategoryService.deleteSubcategory(id);
    }
    @GetMapping("/sort/{sort}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public Set<SubcategoryResponse>sortSubcategory(@PathVariable String sort){
        return subcategoryService.sort(sort);
    }
    @GetMapping("/group/by")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public Set<SubcategoryResponse>groupBy(){
        return subcategoryService.groupBy();
    }



}
