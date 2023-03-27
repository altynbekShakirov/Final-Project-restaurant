package peaksoft.dto.response.pageResponse;

import lombok.Builder;
import peaksoft.entity.Subcategory;

import java.util.List;
@Builder
public record PageSubCategoriesResponse(
        List<Subcategory> subcategories,
        int currentPage,
        int size
) {
}
