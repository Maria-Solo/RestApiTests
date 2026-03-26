package com.mary.models.auth.login;

public record LoginResponse(String accessToken,
                            String refreshToken) {

}
