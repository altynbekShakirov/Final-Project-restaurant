package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CategoryRequest(
        @Size(min = 2, max = 20, message = "Should not be less than 2 more than 30 characters!!")
        @NotEmpty(message = "Category name must not be empty!")
        String name

) {
}
