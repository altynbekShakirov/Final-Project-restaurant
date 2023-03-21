package peaksoft.serivice.seriviceImpl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.jwt.JwtUtil;
import peaksoft.dto.request.ApplicationRequest;
import peaksoft.dto.request.UserInfoRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.EmployeeResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserInfoResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.User;
import peaksoft.entity.enums.Role;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.serivice.UserService;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestaurantRepository restaurantService;

    @Override
    public SimpleResponse saveUser(UserRequest userRequest) {
        Set<UserResponse> allUsers = userRepository.findAllUsers(restaurantService.findRestaurant().getId());
        if (allUsers.size()<=15){
        User user = new User();
        user.setEmail(userRequest.email());
        user.setDateOfBirth(userRequest.dateOfBirth());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setPhoneNumber(userRequest.phoneNumber());
        if (userRequest.experience() >= 4) {
            user.setRole(Role.CHEF);
        }
        if (userRequest.experience() <= 3) {
            user.setRole(Role.WAITER);
        }
        user.setExperience(userRequest.experience());
        user.setRestaurant(restaurantService.findById(restaurantService.findRestaurant().getId()).orElseThrow(()-> new NoSuchElementException("This Restaurant does not exist")));
        userRepository.save(user);
        authenticate(new UserInfoRequest(user.getEmail(), userRequest.password()));
        }else new SimpleResponse(HttpStatus.FORBIDDEN,"No more vacancies!!");


        return new SimpleResponse(HttpStatus.OK, "Successfully User saved!!");
    }

    @Override
    public UserResponse getByUserId(Long id) {
        return userRepository.findByUserId(id);
    }

    @Override
    public Set<UserResponse> getAllUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public Set<UserResponse> getAllUsers(Long restaurantId) {
        return userRepository.findAllUsers(restaurantId);
    }

    @Override
    public SimpleResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("This id:"+id+" does not exist"));
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setDateOfBirth(userRequest.dateOfBirth());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setExperience(userRequest.experience());
        user.setPhoneNumber(userRequest.phoneNumber());
        if (userRequest.experience() <= 2) {
            user.setRole(Role.CHEF);
        }
        if (userRequest.experience() <= 0) {
            user.setRole(Role.WAITER);
        }
        userRepository.save(user);

return new SimpleResponse(HttpStatus.OK,"Successfully updated! ");
    }

    @Override
    public SimpleResponse deleteUser(Long id) {
        userRepository.deleteById(id);
        return new SimpleResponse(HttpStatus.OK,"Successfully deleted!!");
    }


    @Override
    public SimpleResponse application(UserRequest userRequest) {
        Set<UserResponse> allUsers = userRepository.findAllUsers(restaurantService.findRestaurant().getId());
        if (allUsers.size()<=15){
         User user1 = new User();
        user1.setEmail(userRequest.email());
        user1.setDateOfBirth(userRequest.dateOfBirth());
        user1.setPassword(passwordEncoder.encode(userRequest.password()));
        user1.setFirstName(userRequest.firstName());
        user1.setLastName(userRequest.lastName());
        user1.setPhoneNumber(userRequest.phoneNumber());
        if (userRequest.experience() <= 2) {
            user1.setRole(Role.CHEF);
        }
        if (userRequest.experience() <= 1) {
            user1.setRole(Role.WAITER);
        }
        userRepository.save(user1);
        authenticate(new UserInfoRequest(user1.getEmail(), userRequest.password()));
        }else {
            return new SimpleResponse(HttpStatus.FORBIDDEN, "No more vacancies!!");
        }
        return new SimpleResponse(HttpStatus.OK, "Your application has been sent!");
    }

    @PostConstruct
    public void init() {
        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setFirstName("Altynbek");
        user.setLastName("Shakirov");
        user.setPhoneNumber("+996500500500");
        user.setRole(Role.ADMIN);
        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }
    }
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    @Override
    public UserInfoResponse authenticate(UserInfoRequest userInfoRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userInfoRequest.email(),
                        userInfoRequest.password()
                )
        );

        User user = userRepository.findByEmail(userInfoRequest.email())
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("User with email: %s doesn't exists", userInfoRequest.email())));
        String token = jwtUtil.generateToken(user);

        return UserInfoResponse.builder().token(token).email(userInfoRequest.email()).build();
    }
    @Override
    public SimpleResponse applications(ApplicationRequest applicationRequest) {
        User user = userRepository.findById(applicationRequest.id()).orElseThrow(()->new NoSuchElementException("This id:"+applicationRequest.id()+" does not exist"));

        if (applicationRequest.accepted()){
            user.setRestaurant(restaurantService.findById(restaurantService.findRestaurant().getId()).orElseThrow(()-> new NoSuchElementException("This Restaurant does not exist")));
            userRepository.save(user);
            return new SimpleResponse(HttpStatus.OK,"Congratulations you have successfully got a job!!");
        }else {
           userRepository.delete(user);
            return new SimpleResponse(HttpStatus.OK,"You couldn't get a job");

        }

    }

    @Override
    public Set<EmployeeResponse> getAllApplications() {
        return userRepository.getAllApplications();
    }


}
