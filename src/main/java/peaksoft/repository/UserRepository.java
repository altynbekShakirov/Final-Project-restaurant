package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.EmployeeResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.User;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select new peaksoft.dto.response.UserResponse(u.firstName,u.lastName,u.dateOfBirth,u.email,u.phoneNumber,u.role)from User u where u.id=:id")
    UserResponse findByUserId(Long id);
    @Query("select new peaksoft.dto.response.UserResponse(u.firstName,u.lastName,u.dateOfBirth,u.email,u.phoneNumber,u.role)from User u where u.restaurant.id=:id")
Set<UserResponse>findAllUsers(Long id);
    @Query("select new peaksoft.dto.response.UserResponse(u.firstName,u.lastName,u.dateOfBirth,u.email,u.phoneNumber,u.role)from User u ")
    Set<UserResponse>findAllUsers();
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    @Query("select new peaksoft.dto.response.EmployeeResponse(u.id,u.firstName,u.lastName,u.dateOfBirth,u.email,u.phoneNumber,u.experience) from User u where u.restaurant=null ")
    Set<EmployeeResponse> getAllApplications();
    @Query("select u from User u where u.id=:id")
    User findByIdQuery(Long id);

}