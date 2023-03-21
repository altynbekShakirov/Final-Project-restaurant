package peaksoft.dto.response;

import lombok.Builder;

@Builder
public record ChequeOfRestaurantAmountDayResponse(
        int numberOfWaiters,
        int numberOfCheque,
        int totalAmount
) {
}
