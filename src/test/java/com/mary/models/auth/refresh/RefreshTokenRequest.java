package com.mary.models.auth.refresh;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class RefreshTokenRequest {
    private String refreshToken;
}
