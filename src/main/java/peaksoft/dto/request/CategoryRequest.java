package peaksoft.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record CategoryRequest(
        String name,
        List<String> subcategory

) {
}
