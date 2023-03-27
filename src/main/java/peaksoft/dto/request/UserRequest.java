package peaksoft.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserRequest(

        @Size(min = 3, max = 30, message = "Should not be less than 2 more than 30 characters!!")
        @NotEmpty(message = "First name must not be empty!")
        String firstName,
        @Size(min = 3, max = 30, message = "Should not be less than 2 more than 30 characters!!")
        @NotEmpty(message = "Last name must not be empty!")
        String lastName,
        LocalDate dateOfBirth,

        @Email(message = "Invalid email address")
        @NotEmpty(message = "Email must not be empty!")
        String email,
        @NotEmpty(message = "password must not be empty!")
        String password,

        @NotEmpty(message = "Phone number must not be empty!")
        String phoneNumber,
        @NotNull(message = "Experience must not be empty!")
        int experience,
        @NotEmpty(message = "Role must not be empty!")

        String role


) {
}
