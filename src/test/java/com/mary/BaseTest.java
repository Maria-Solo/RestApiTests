package com.mary;

import com.mary.client.AuthApiClient;
import com.mary.models.auth.login.LoginRequest;
import com.mary.models.auth.login.LoginResponse;
import com.mary.specs.RequestSpec;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.config.RestAssuredConfig.config;

public class BaseTest {

    @BeforeAll
    public static void setup() {
        RestAssured.requestSpecification = RequestSpec.requestSpec();
        /// тут вызываем метод авторизации
        AuthApiClient authApiClient = new AuthApiClient();
        LoginRequest loginRequest = new LoginRequest();
        LoginResponse loginResponse = authApiClient.login(loginRequest);

       String  accessToken = loginResponse.accessToken();
       String refreshToken = loginResponse.refreshToken();

       RestAssured.requestSpecification.header("Authorization", "Bearer " + accessToken);

    }
}
