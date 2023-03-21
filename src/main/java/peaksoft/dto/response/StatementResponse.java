package peaksoft.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import peaksoft.entity.enums.Role;

import java.time.LocalDate;
@Builder
public record StatementResponse(
        Long id,
        String firstName,
        String lastName,
        LocalDate dateOfBrith,
        String email,
        String phoneNumber,
        @Enumerated(EnumType.STRING)
        Role role,
        Integer experience
) {
}
