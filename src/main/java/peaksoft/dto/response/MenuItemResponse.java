package peaksoft.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record MenuItemResponse(
        String name,
        String image,
        BigDecimal price,
        String description,
        boolean isVegetarian
) {
}
