package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.serivice.RestaurantService;

import java.util.Set;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService service;
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse saveRestaurant(@RequestBody RestaurantRequest restaurantRequest){
        return service.saveRestaurant(restaurantRequest);
    }
    @GetMapping()
    @PreAuthorize("permitAll()")
    public RestaurantResponse getAll(){
        return service.getAllRestaurant();
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequest restaurant){
        return service.updateRestaurantById(id,restaurant);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteRestaurant(@PathVariable Long id){
        return service.deleteRestaurant(id);
    }
    @GetMapping("/count")
    @PreAuthorize("permitAll()")
    public String count(){
        return service.count();
    }



}
