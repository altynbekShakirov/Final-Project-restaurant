package peaksoft.dto.response.pageResponse;

import lombok.Builder;
import peaksoft.entity.Category;

import java.util.List;



@Builder
public record PageCategoryResponse(
        List<Category> categories ,
        int currentPage,
        int size

) {
}
