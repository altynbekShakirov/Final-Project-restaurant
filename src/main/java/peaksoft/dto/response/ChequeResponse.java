package peaksoft.dto.response;

import lombok.Builder;
import peaksoft.entity.MenuItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
@Builder
public record ChequeResponse(
        String  fullName,
        Set<MenuItem> items,
        int price,
        int service
) {
}
