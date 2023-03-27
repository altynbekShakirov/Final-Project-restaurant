package peaksoft.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ApplicationRequest(
        @NotNull
        Long id,
        @NotNull
        Boolean accepted
) {
}
