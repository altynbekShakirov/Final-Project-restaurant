package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record ChequeUpdateRequest(
        @NotEmpty(message = " Menu items must not be empty!")
        Set<String> menuItemSet

) {
}
