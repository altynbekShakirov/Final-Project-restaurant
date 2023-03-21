package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.request.StopListUpdateRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.serivice.StopListService;

import java.util.Set;

@RestController
@RequestMapping("/api/stop/list")
@RequiredArgsConstructor
public class StopListController {
    private final StopListService service;

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse saveStopList(@PathVariable Long id, @RequestBody StopListRequest stopListRequest) {
        return service.saveStopList(id, stopListRequest);
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
    public SimpleResponse updateStopList(@PathVariable Long id, @RequestBody StopListUpdateRequest stopListUpdateRequest) {
        return service.update(id, stopListUpdateRequest);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public  SimpleResponse deleteStopList(@PathVariable Long id){
        return service.delete(id);
    }
}
