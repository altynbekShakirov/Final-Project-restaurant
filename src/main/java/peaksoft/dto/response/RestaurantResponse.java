package peaksoft.dto.response;

import lombok.Builder;

@Builder
public record RestaurantResponse(
        String name,
        String location,
        String restType,
        int service,
        int numberOfEmployees
) {
}
