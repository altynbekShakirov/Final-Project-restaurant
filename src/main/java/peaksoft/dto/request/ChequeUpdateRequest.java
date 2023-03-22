package peaksoft.dto.request;

import java.util.Set;

public record ChequeUpdateRequest(
        Set<String> menuItemSet
) {
}
