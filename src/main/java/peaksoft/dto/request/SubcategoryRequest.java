package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record SubcategoryRequest(
        @NotEmpty(message = " Name must not be empty!")

        String name,
        @NotEmpty(message = " Category name must not be empty!")
        String categoryName
) {
}
