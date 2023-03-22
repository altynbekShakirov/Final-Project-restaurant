package peaksoft.dto.request;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserRequest(

//        @NotBlank
        String firstName,
//        @NotBlank

        String lastName,
//        @NotBlank
        LocalDate dateOfBirth,

//        @Email
//        @Column(unique = true)
        String email,
//        @NotBlank

        String password,

//        @NotBlank
        String phoneNumber,
//        @NotBlank
        int experience,
        String role


) {
}
