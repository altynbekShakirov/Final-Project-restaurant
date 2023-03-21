package peaksoft.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MenuItemResponseSearch(
        String categoryName,
        String subcategoryName,
        String menuItemName,
        BigDecimal price

) {
}
