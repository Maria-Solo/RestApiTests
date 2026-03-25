package com.mary.client;

import com.mary.models.auth.refresh.RefreshTokenRequest;
import com.mary.models.auth.refresh.RefreshTokenResponse;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class AuthApiClient {
    /// тут будет метод для авторизации
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
}
