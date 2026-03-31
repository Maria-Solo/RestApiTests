package com.mary.client;

import com.mary.models.auth.login.LoginRequest;
import com.mary.models.auth.login.LoginResponse;
import com.mary.models.auth.refresh.RefreshTokenRequest;
import com.mary.models.auth.refresh.RefreshTokenResponse;
import com.mary.models.auth.register.RegisterRequest;
import com.mary.models.auth.register.RegisterResponse;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class AuthApiClient {

    public RefreshTokenResponse getRefreshToken(RefreshTokenRequest refreshTokenRequest) {
        return given()
                .body(refreshTokenRequest)
                .post("/auth/refresh")
                .then()
                //тут возвращается ответ в формате json
                .statusCode(200)
                //распаковываем body как класс
                //можно получить токен getAccess getRefresh
                .extract().body().as(RefreshTokenResponse.class);
    };

    public LoginResponse login(LoginRequest loginRequest) {
        return given()
                .body(loginRequest)
                .log().all()  // Логируем запрос для отладки
                .post("/auth/login")
                .then()
                .log().all()  // Логируем ответ для отладки
                .statusCode(200)
                .extract().body().as(LoginResponse.class);
    }

    public RegisterResponse register(RegisterRequest registerRequest) {
        return given()
                .body(registerRequest)
                .post("/auth/register")
                .then()
                .statusCode(201)
                .extract().body().as(RegisterResponse.class);
    }
}
