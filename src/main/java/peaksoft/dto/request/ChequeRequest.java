package peaksoft.dto.request;

import java.util.Set;

public record ChequeRequest(
        Set<String> foods,
        String email


) {
}
