package peaksoft.dto.request;

import java.time.LocalDate;

public record StopListUpdateRequest(
        String reason,
        LocalDate date,
            String name
) {
}
