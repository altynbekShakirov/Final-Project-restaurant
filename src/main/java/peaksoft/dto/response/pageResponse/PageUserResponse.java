package peaksoft.dto.response.pageResponse;

import lombok.Builder;
import peaksoft.entity.User;

import java.util.List;
@Builder
public record PageUserResponse(
        List<User> users,
        int currentPage,
        int size
) {
}
