package co.istad.polin.front_bff.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        Boolean isAuthenticated
) {
}
