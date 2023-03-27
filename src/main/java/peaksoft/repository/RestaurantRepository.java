package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.entity.Restaurant;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("select new peaksoft.dto.response.RestaurantResponse(r.name,r.location,r.restType,r.service,r.numberOfEmployees) from Restaurant r")
    RestaurantResponse findAllRestaurants();

    @Query("select  r from  Restaurant  r")
    Restaurant findRestaurant();

    @Query("select case when count(r) > 0 then true else false end from Restaurant r where r.name is not null")
    Boolean existsRestaurant();

    @Query("select  r from  Restaurant  r")
    Optional<Restaurant> findRestaurants();
}