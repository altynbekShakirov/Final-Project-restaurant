package peaksoft.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record ChequeRequest(
        @NotEmpty(message = "Foods must not be empty!")
        Set<String> foods,
        @NotEmpty(message = " Email must not be empty!")
        @Email(message = "Invalid email address")
        String email


) {
}
