package peaksoft.dto.response.pageResponse;


import lombok.Builder;
import peaksoft.entity.Cheque;

import java.util.List;
@Builder
public record PageChequeResponse(
        List<Cheque> cheques ,
        int currentPage,
        int size
) {
}
