package co.istad.polin.front_bff.dto;

import lombok.Builder;

@Builder
public record ProfileResponse(
        String username,
        String email,
        String gender,
        String familyName,
        String givenName,
        String phoneNumber,
        String dob
) {
}