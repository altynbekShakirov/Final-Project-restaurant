package peaksoft.serivice;

import peaksoft.dto.request.ApplicationRequest;
import peaksoft.dto.request.UserInfoRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.*;

import java.util.Set;

public interface UserService {

    SimpleResponse saveUser(UserRequest userRequest);

    UserResponse getByUserId(Long id);

    Set<UserResponse> getAllUsers();

    Set<UserResponse> getAllUsers(Long restaurantId);

    SimpleResponse updateUser(Long id, UserRequest userRequest);

    SimpleResponse deleteUser(Long id);

    SimpleResponse application(UserRequest userRequest);

    UserInfoResponse authenticate(UserInfoRequest userInfoRequest);

    SimpleResponse applications(ApplicationRequest applicationRequest);

    Set<EmployeeResponse> getAllApplications();


}
