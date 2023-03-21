package peaksoft.dto.request;

import peaksoft.entity.MenuItem;

import java.util.Set;

public record ChequeUpdateRequest(
        Set<String> menuItemSet
) {
}
