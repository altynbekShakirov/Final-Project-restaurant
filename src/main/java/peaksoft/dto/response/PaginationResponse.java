package peaksoft.dto.response;

import lombok.Builder;
import peaksoft.entity.MenuItem;

import java.util.List;

@Builder
public record PaginationResponse(
        List<MenuItem> list,
        int currentPage,
        int pageSize

) {
}
