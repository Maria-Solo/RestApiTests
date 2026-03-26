package com.mary.models.auth.login;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class LoginRequest {
    private String email = "admin@crm.local";
    private String password = "admin123";
}
