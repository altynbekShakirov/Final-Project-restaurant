package peaksoft.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ChequeOfRestaurantAmountDayRequest(
        @NotNull
        LocalDate date
) {
}
