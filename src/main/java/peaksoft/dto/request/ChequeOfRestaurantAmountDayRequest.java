package peaksoft.dto.request;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record ChequeOfRestaurantAmountDayRequest(
        LocalDate date
) {
}
