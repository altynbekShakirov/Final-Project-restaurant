package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StopListRequest(
        @NotEmpty(message = " Reason must not be empty!")
        String reason,
        @NotNull
        LocalDate date,
        @NotEmpty(message = " Manu name must not be empty!")
        String menuName

) {
}
