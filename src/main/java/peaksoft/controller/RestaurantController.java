package peaksoft.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.exception.AlreadyExistException;
import peaksoft.serivice.RestaurantService;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
    private final RestaurantService service;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse saveRestaurant(@RequestBody @Valid RestaurantRequest restaurantRequest) throws AlreadyExistException {
        return service.saveRestaurant(restaurantRequest);
    }

    @GetMapping()
    @PreAuthorize("permitAll()")
    public RestaurantResponse getAll() {
        return service.getAllRestaurant();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse updateRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantRequest restaurant) {
        return service.updateRestaurantById(id, restaurant);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteRestaurant() {
        return service.deleteRestaurant();
    }

    @GetMapping("/count")
    @PreAuthorize("permitAll()")
    public String count() {
        return service.count();
    }

}
