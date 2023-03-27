package peaksoft.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ApplicationRequest;
import peaksoft.dto.request.UserInfoRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.EmployeeResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserInfoResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.response.pageResponse.PageUserResponse;
import peaksoft.exception.NoVacancyException;
import peaksoft.serivice.UserService;

import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/login")
    public UserInfoResponse register(@RequestBody @Valid UserInfoRequest request) {
        return service.authenticate(request);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse saveUser(@RequestBody @Valid UserRequest userRequest) throws NoVacancyException {
        return service.saveUser(userRequest);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return service.getByUserId(id);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public Set<UserResponse> getAll() {
        return service.getAllUsers();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest userRequest) {
        return service.updateUser(id, userRequest);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public SimpleResponse deleteUser(@PathVariable Long id) {
        return service.deleteUser(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/applications")
    public Set<EmployeeResponse> getAllApplications() {
        return service.getAllApplications();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/accepted")
    public SimpleResponse applications(@RequestBody @Valid ApplicationRequest applicationRequest) {
        return service.applications(applicationRequest);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/application")
    public SimpleResponse application(@RequestBody @Valid UserRequest userRequest) throws NoVacancyException {
        return service.application(userRequest);
    }

    @GetMapping("/workers")
    @PreAuthorize("permitAll()")
    public Set<UserResponse> getAllRestaurantJobs() {
        return service.getAllWorkers();
    }

    @GetMapping("/page")
    public PageUserResponse getUserResponse(@RequestParam int page, @RequestParam int size) {
        return service.getUserPage(page, size);
    }
}
