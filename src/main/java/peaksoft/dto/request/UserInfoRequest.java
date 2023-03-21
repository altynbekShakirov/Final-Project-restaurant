package peaksoft.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserInfoRequest(
        @Email
        String email,
        @Size(min = 4)
        String password
) {
}
