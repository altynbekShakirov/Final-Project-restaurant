package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record RestaurantRequest(
        @NotEmpty(message = "Name must not be empty!")
        String name,
        @NotEmpty(message = "Location must not be empty!")
        String location,
        @NotEmpty(message = "Rest type must not be empty!")
        String restType,

        int service


) {
}
