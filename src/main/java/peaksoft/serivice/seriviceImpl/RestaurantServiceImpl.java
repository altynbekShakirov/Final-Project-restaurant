package peaksoft.serivice.seriviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.Restaurant;

import peaksoft.entity.enums.Role;
import peaksoft.repository.RestaurantRepository;
import peaksoft.serivice.RestaurantService;
import peaksoft.serivice.UserService;

import java.util.NoSuchElementException;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repository;
    private final UserService userService;

    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {

        if (!repository.existsRestaurant()){
                Restaurant restaurant = new Restaurant();
                restaurant.setName(restaurantRequest.name());
                restaurant.setLocation(restaurantRequest.location());
                restaurant.setRestType(restaurantRequest.restType());
                restaurant.setService(restaurantRequest.service());
                restaurant.setNumberOfEmployees(userService.getAllUsers().size());
                repository.save(restaurant);
        }else {
            return new SimpleResponse(HttpStatus.FORBIDDEN,"uje restaran bar");
        }

                return SimpleResponse.builder().status(HttpStatus.OK)
                        .message("Successfully saved").build();
    }


    @Override
    public RestaurantResponse getAllRestaurant() {
        return repository.findAllRestaurants();
    }

    @Override
    public SimpleResponse updateRestaurantById(Long id, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Hello my name is Null"));
        restaurant.setName(restaurantRequest.name());
        restaurant.setService(restaurantRequest.service());
        restaurant.setRestType(restaurantRequest.restType());
        restaurant.setLocation(restaurantRequest.location());
        repository.save(restaurant);
        return new SimpleResponse(HttpStatus.OK, "Successfully updated!!!");
    }

    @Override
    public SimpleResponse deleteRestaurant(Long id) {
        repository.deleteById(id);
        return new SimpleResponse(HttpStatus.OK, "Successfully deleted!");
    }

    @Override
    public String count() {
        Set<UserResponse> allUsers = userService.getAllUsers(repository.findRestaurant().getId());
        int countChef=0;
        int countWaiter=0;
        for (UserResponse allUser : allUsers) {
            if (allUser.role().equals(Role.WAITER)) {
                countWaiter++;
            }
            if (allUser.role().equals(Role.CHEF)) {
                countChef++;
            }
        }
        return "Currently the restaurant has "+allUsers.size()+" employees .\n" +
                "Chefs: "+countChef
                +"\nWaiters: "+countWaiter;
    }
}
