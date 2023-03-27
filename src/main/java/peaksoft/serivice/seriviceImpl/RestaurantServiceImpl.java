package peaksoft.serivice.seriviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.Restaurant;
import peaksoft.entity.enums.Role;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.serivice.RestaurantService;
import peaksoft.serivice.UserService;

import java.util.Set;


@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repository;
    private final UserService userService;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) throws AlreadyExistException {

        if (!repository.existsRestaurant()) {
            Restaurant restaurant = new Restaurant();
            restaurant.setName(restaurantRequest.name());
            restaurant.setLocation(restaurantRequest.location());
            restaurant.setRestType(restaurantRequest.restType());
            restaurant.setService(restaurantRequest.service());
            repository.save(restaurant);
        } else {
            throw new AlreadyExistException("Restaurant already exists!!");
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
        Restaurant restaurant = repository.findById(id).orElseThrow(() -> new NotFoundException("Hello my name is Null"));
        restaurant.setName(restaurantRequest.name());
        restaurant.setService(restaurantRequest.service());
        restaurant.setRestType(restaurantRequest.restType());
        restaurant.setLocation(restaurantRequest.location());
        repository.save(restaurant);
        return new SimpleResponse(HttpStatus.OK, "Successfully updated!!!");
    }

    @Override
    public SimpleResponse deleteRestaurant() {
        repository.deleteAll();
        return new SimpleResponse(HttpStatus.OK, "Successfully deleted!");
    }

    @Override
    public String count() {
        Set<UserResponse> allUsers = userService.getAllWorkers();
        int countChef = 0;
        int countWaiter = 0;
        for (UserResponse allUser : allUsers) {
            if (allUser.role().equals(Role.WAITER)) {
                countWaiter++;
            }
            if (allUser.role().equals(Role.CHEF)) {
                countChef++;
            }
        }
        return "Currently the restaurant has " + allUsers.size() + " employees .\n" +
                "Chefs: " + countChef
                + "\nWaiters: " + countWaiter;
    }
}
