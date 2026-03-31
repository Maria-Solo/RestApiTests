package com.mary.models.auth.register;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegisterRequest {
    String email = "old.user@example.com";
    String password = "secret1234";
    String confirmPassword = "secret1234";
}
