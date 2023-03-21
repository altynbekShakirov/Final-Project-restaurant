package peaksoft.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EmployeeResponse(
        Long id,
        String firstName,
        String lastName,
        LocalDate dateOfBrith,
        String email,
        String phoneNumber,
        Integer experience
) {
}
