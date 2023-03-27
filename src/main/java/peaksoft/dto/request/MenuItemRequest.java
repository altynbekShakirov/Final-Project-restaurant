package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

public record MenuItemRequest(
        @NotEmpty(message = "Name must not be empty!")
        String name,
        @NotEmpty(message = "Image must not be empty!")
        String image,

        BigDecimal price,
        @NotEmpty(message = " Email must not be empty!")
        String description,

        boolean isVegetarian,
        @NotEmpty(message = " Subcategory must not be empty!")
        String subcategory

) {
}
