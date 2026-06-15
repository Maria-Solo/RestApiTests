package com.mary;

import com.mary.models.auth.login.LoginRequest;
import com.mary.models.auth.login.LoginResponse;
import io.restassured.http.ContentType;


import java.util.Map;

public class BaseTest {

    private HttpController httpController = new HttpController();

    protected Map<String, String> getAuthHeaders(String email, String password) {
        var loginRequest = new LoginRequest()
                .setEmail(email)
                .setPassword(password);
        String accessToken = httpController.sendRequest("http://localhost:8080/api/auth/login", HttpController.HttpMethod.POST, null, loginRequest, ContentType.JSON)
                .extract().as(LoginResponse.class).accessToken();
        return Map.of("Authorization", "Bearer " + accessToken);
    }
}
