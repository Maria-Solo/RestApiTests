package com.mary.models.auth.register;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegisterRequest {
    String email;
    String password;
    String confirmPassword;
}
