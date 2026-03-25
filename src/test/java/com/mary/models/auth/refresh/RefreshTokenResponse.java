package com.mary.models.auth.refresh;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken
) {

}
