package peaksoft.dto.response;

import lombok.Builder;

@Builder
public record UserInfoResponse(
        String email,
        String token
) {
}
