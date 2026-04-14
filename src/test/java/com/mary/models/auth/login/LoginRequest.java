package com.mary.models.auth.login;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class LoginRequest {
    private String email;
    private String password;
}
