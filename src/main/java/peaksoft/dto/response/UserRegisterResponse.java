package peaksoft.dto.response;

import lombok.Builder;

@Builder
public record UserRegisterResponse(
        String email,
        String token
) {
}
