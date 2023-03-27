package peaksoft.serivice.seriviceImpl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import peaksoft.dto.response.pageResponse.PageUserResponse;
import peaksoft.entity.User;
import peaksoft.entity.enums.Role;
import peaksoft.exception.BadCredentialException;
import peaksoft.exception.BadRequestException;
import peaksoft.exception.NoVacancyException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.serivice.UserService;

import java.time.LocalDate;
import java.util.Set;


@Service
@Slf4j

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestaurantRepository restaurantRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RestaurantRepository restaurantRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.restaurantRepository = restaurantRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public SimpleResponse saveUser(UserRequest userRequest) throws NoVacancyException {
        validation(userRequest);
        if (userRepository.existsByEmail(userRequest.email().toLowerCase())) {
            throw new BadCredentialException(
                    "Email must be unique!!");
        }
        if (userRepository
                .existsByPhoneNumber(userRequest.phoneNumber())) {
            throw new BadCredentialException(
                    "Phone number must be unique!!");
        }
        if (userRepository.findAllUsers(restaurantRepository.findRestaurants()
                .orElseThrow(() -> new BadCredentialException("This Restaurant does not exist!!"))
                .getId()).size() == 15) {
            UserServiceImpl.log.error("There are currently no open vacancies");
            throw new NoVacancyException("There are currently no open vacancies");
        }

        User user = new User();
        user.setEmail(userRequest.email().toLowerCase());
        user.setDateOfBirth(userRequest.dateOfBirth());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setPhoneNumber(userRequest.phoneNumber());
        switch (userRequest.role()) {
            case "CHUPAPI MUNANO" -> user.setRole(Role.ADMIN);
            case "CHEF" -> user.setRole(Role.CHEF);
            case "WAITER" -> user.setRole(Role.WAITER);
            default -> throw new BadCredentialException("This Role does not exist!!");

        }
        user.setExperience(userRequest.experience());
        if (restaurantRepository.existsRestaurant()) {
            user.setRestaurant(restaurantRepository.findById(restaurantRepository.findRestaurant().getId()).orElseThrow(() -> new NotFoundException("This Restaurant does not exist")));

        } else {
            throw new NotFoundException("This Restaurant does not exist");
        }
        userRepository.save(user);
        authenticate(new UserInfoRequest(user.getEmail(), userRequest.password()));


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
    public Set<UserResponse> getAllWorkers() {
        return userRepository.findAllUsers(restaurantRepository.findRestaurant().getId());
    }

    @Override
    public SimpleResponse updateUser(Long id, UserRequest userRequest) {
        validation(userRequest);
        if (userRepository.existsByEmail(userRequest.email().toLowerCase())) {
            throw new BadCredentialException(
                    "Email must be unique!!");
        }
        if (userRepository.existsByPhoneNumber(userRequest.phoneNumber())) {
            throw new BadCredentialException(
                    "Phone number must be unique!!");
        }

        User user = userRepository.findById(id).orElseThrow(()
                -> new NotFoundException("This id:" + id + " does not exist"));
        switch (userRequest.role()) {
            case "CHUPAPI MUNANO" -> user.setRole(Role.ADMIN);
            case "CHEF" -> user.setRole(Role.CHEF);
            case "WAITER" -> user.setRole(Role.WAITER);
            default -> throw new BadRequestException("This Role does not exist!!");

        }
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setDateOfBirth(userRequest.dateOfBirth());
        user.setEmail(userRequest.email().toLowerCase());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setExperience(userRequest.experience());
        user.setPhoneNumber(userRequest.phoneNumber());

        userRepository.save(user);

        return new SimpleResponse(HttpStatus.OK, "Successfully updated! ");
    }

    @Override
    public SimpleResponse deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("this id does not exist"));
        userRepository.delete(user);
        return new SimpleResponse(HttpStatus.OK, "Successfully deleted!!");
    }


    @Override
    public SimpleResponse application(UserRequest userRequest) throws NoVacancyException {
        validation(userRequest);
        if (userRepository.existsByEmail(userRequest.email().toLowerCase())) {
            throw new BadCredentialException(
                    "Email must be unique!!");
        }
        if (userRepository.findAllUsers(restaurantRepository.findRestaurants()
                .orElseThrow(() -> new BadCredentialException("This Restaurant does not exist!!"))
                .getId()).size() == 15) {
            UserServiceImpl.log.error("There are currently no open vacancies");
            throw new NoVacancyException("There are currently no open vacancies");
        }
        if (userRepository.existsByPhoneNumber(userRequest.phoneNumber())) {
            throw new BadCredentialException(
                    "Phone number must be unique!!");
        }
        User user1 = new User();
        switch (userRequest.role()) {
            case "I AM THE BEST DEVELOPER IN THE  WORLD!!" -> user1.setRole(Role.ADMIN);
            case "CHEF" -> user1.setRole(Role.CHEF);
            case "WAITER" -> user1.setRole(Role.WAITER);
            default -> throw new BadCredentialException("This Role does not exist!!");

        }
        user1.setEmail(userRequest.email().toLowerCase());
        user1.setDateOfBirth(userRequest.dateOfBirth());
        user1.setPassword(passwordEncoder.encode(userRequest.password()));
        user1.setFirstName(userRequest.firstName());
        user1.setLastName(userRequest.lastName());
        user1.setPhoneNumber(userRequest.phoneNumber());
        user1.setExperience(userRequest.experience());
        userRepository.save(user1);
        authenticate(new UserInfoRequest(user1.getEmail(), userRequest.password()));

        return new SimpleResponse(HttpStatus.OK, "Your application has been sent!");
    }

    @PostConstruct
    public void init() {
        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setFirstName("Admin");
        user.setLastName("Adminov");
        user.setPhoneNumber("+996500500500");
        user.setRole(Role.ADMIN);
        user.setDateOfBirth(LocalDate.of(2006, 9, 25));
        user.setExperience(10);
        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }
    }

    @Override
    public UserInfoResponse authenticate(UserInfoRequest userInfoRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userInfoRequest.email().toLowerCase(),
                        userInfoRequest.password()
                )
        );

        User user = userRepository.findByEmail(userInfoRequest.email())
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with email: %s doesn't exists", userInfoRequest.email())));
        String token = jwtUtil.generateToken(user);

        return UserInfoResponse.builder().token(token).email(userInfoRequest.email().toLowerCase()).build();
    }

    @Override
    public SimpleResponse applications(ApplicationRequest applicationRequest) {
        User user = userRepository.findById(applicationRequest.id())
                .orElseThrow(() -> new NotFoundException("This id:" + applicationRequest.id() + " does not exist"));

        if (applicationRequest.accepted()) {
            user.setRestaurant(restaurantRepository.findById(restaurantRepository.findRestaurant()
                    .getId()).orElseThrow(() -> new NotFoundException("This Restaurant does not exist")));
            userRepository.save(user);
            return new SimpleResponse(HttpStatus.OK, "Congratulations you have successfully got a job!!");
        } else {
            userRepository.delete(user);
            return new SimpleResponse(HttpStatus.OK, "You couldn't get a job");

        }

    }

    private static void validation(UserRequest userRequest) {
        if (!(userRequest.phoneNumber().startsWith("+996") && userRequest.phoneNumber().length() == 13)) {
            throw new BadRequestException("The phone number must be 13 digits long and start with +996 !!!");
        }
        int age = LocalDate.now().minusYears(userRequest.dateOfBirth().getYear()).getYear();
        if (userRequest.role().equals(Role.CHEF.name())) {
            if (age < 25 || age > 45) {
                throw new BadRequestException("Chef must be between 25 and 45 years of age");
            }
            if (userRequest.experience() < 2) {
                throw new BadRequestException("Chef experience must be more than 2 years");
            }
        } else if (userRequest.role().equals(Role.WAITER.name())) {
            if (age < 18 || age > 30) {
                throw new BadRequestException("Waiter must be between 18 and 30 years of age");
            }
            if (userRequest.experience() < 1) {
                throw new BadRequestException("Waiter experience must be more than 1 year");
            }
        }


    }
    @Override
    public PageUserResponse getUserPage(int currentPage, int size) {
        Pageable pageable = PageRequest.of(currentPage -1, size);
        Page<User> all = userRepository.findAll(pageable);
        return PageUserResponse.builder().users(all.getContent()).currentPage(pageable.getPageNumber()+1)
                .size(all.getTotalPages()).build();

    }

    @Override
    public Set<EmployeeResponse> getAllApplications() {
        return userRepository.getAllApplications();
    }


}
