package peaksoft.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record ChequeOneDayTotalAmountResponse(
        String waiterFullName,
        int numberOfCheques,
        BigDecimal totalAmount
) {
}
