package peaksoft.dto.request;

import lombok.Builder;

@Builder
public record ApplicationRequest(
  Long id,
  Boolean accepted
) {
}
