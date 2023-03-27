package peaksoft.serivice;

import peaksoft.dto.request.ApplicationRequest;
import peaksoft.dto.request.UserInfoRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.EmployeeResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserInfoResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.response.pageResponse.PageUserResponse;
import peaksoft.exception.NoVacancyException;

import java.util.Set;

public interface UserService {

    SimpleResponse saveUser(UserRequest userRequest) throws NoVacancyException;

    UserResponse getByUserId(Long id);

    Set<UserResponse> getAllUsers();

    Set<UserResponse> getAllWorkers();

    SimpleResponse updateUser(Long id, UserRequest userRequest);

    SimpleResponse deleteUser(Long id);

    SimpleResponse application(UserRequest userRequest) throws NoVacancyException;

    UserInfoResponse authenticate(UserInfoRequest userInfoRequest);
    PageUserResponse getUserPage(int currentPage, int size);

    SimpleResponse applications(ApplicationRequest applicationRequest);

    Set<EmployeeResponse> getAllApplications();


}
