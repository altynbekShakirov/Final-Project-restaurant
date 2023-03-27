package peaksoft.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.dto.response.pageResponse.PageStopListResponse;
import peaksoft.serivice.StopListService;

import java.util.Set;

@RestController
@RequestMapping("/api/stop/list")
@RequiredArgsConstructor
public class StopListController {
    private final StopListService service;

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse saveStopList( @RequestBody @Valid StopListRequest stopListRequest) {
        return service.saveStopList( stopListRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public StopListResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public Set<StopListResponse> getAllStopList() {
        return service.getAllStopLists();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse updateStopList(@PathVariable Long id, @RequestBody @Valid StopListRequest stopListRequest) {
        return service.update(id, stopListRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse deleteStopList(@PathVariable @Valid Long id) {
        return service.delete(id);
    }
    @GetMapping("/page")
    public PageStopListResponse getStopListResponse(@RequestParam int page, @RequestParam int size){
        return service.getStopListPage(page,size);
    }
}
