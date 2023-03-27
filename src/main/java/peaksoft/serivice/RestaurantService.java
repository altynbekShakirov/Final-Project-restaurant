package peaksoft.serivice;

import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.exception.AlreadyExistException;

public interface RestaurantService {
    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) throws AlreadyExistException;

    RestaurantResponse getAllRestaurant();

    SimpleResponse updateRestaurantById(Long id, RestaurantRequest restaurantRequest);

    SimpleResponse deleteRestaurant();

    String count();

}
