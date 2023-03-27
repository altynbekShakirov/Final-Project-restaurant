package peaksoft.dto.response.pageResponse;

import lombok.Builder;
import peaksoft.entity.StopList;

import java.util.List;
@Builder
public record PageStopListResponse(

        List<StopList>stopLists,
        int currentPage,
        int size
) {
}
