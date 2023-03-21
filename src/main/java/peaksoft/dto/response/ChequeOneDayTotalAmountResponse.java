package peaksoft.dto.response;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Builder
public record ChequeOneDayTotalAmountResponse(
        String walterFullName,
        int numberOfCheques,
        BigDecimal totalAmount
) {
}
