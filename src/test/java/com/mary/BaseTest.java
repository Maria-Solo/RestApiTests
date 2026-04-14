package com.mary;

import com.mary.client.AuthApiClient;
import com.mary.client.ClientApiClient;
import com.mary.models.Client;
import com.mary.models.auth.login.LoginRequest;
import com.mary.models.auth.login.LoginResponse;
import com.mary.models.auth.refresh.RefreshTokenRequest;
import com.mary.models.auth.register.RegisterRequest;
import com.mary.models.auth.register.RegisterResponse;
import com.mary.specs.RequestSpec;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.config.RestAssuredConfig.config;

public class BaseTest {

    private AuthApiClient authApiClient = new AuthApiClient();
    private HttpController httpController= new HttpController();




/*
    //@BeforeAll
    public static void setup() {
        var authHeaders = getAuthHeaders("admin@crm.local", "admin123");
        RestAssured.requestSpecification = RequestSpec.requestSpec(authHeaders);

//        LoginResponse loginResponse = authApiClient.login(loginRequest);
//
//       String  accessToken = loginResponse.accessToken();
//       String refreshToken = loginResponse.refreshToken();
//
//       RestAssured.requestSpecification.header("Authorization", "Bearer " + accessToken);
    }

    @Test
    public void registerUserTest(){
        RestAssured.requestSpecification = RequestSpec.requestSpec();
//        RestAssured.requestSpecification.header("Authorization", "Bearer " + accessToken);

        RegisterResponse registerResponse = authApiClient.register(registerRequest);

        String accessToken =  registerResponse.accessToken();
        String refreshToken = registerResponse.refreshToken();

    }

    @Test
    public void getRefreshTokenTest(){
        RestAssured.requestSpecification = RequestSpec.requestSpec();
        /// тут вызываем метод авторизации
        AuthApiClient authApiClient = new AuthApiClient();
        LoginRequest loginRequest = new LoginRequest();
        String refreshToken = authApiClient.getRefreshTokenViaAuth(loginRequest);
        System.out.println(refreshToken);
    }

    @Test
    void shouldGetNewAccessTokenViaRefreshToken(){
        var refreshToken = authApiClient.login(loginRequest).refreshToken();
        var newAccessToken = authApiClient.getRefreshToken(new RefreshTokenRequest().setRefreshToken(refreshToken));

    }
    @Test
    void test(){
//        RestAssured.requestSpecification = RequestSpec.requestSpec();
//        RestAssured.requestSpecification.headers(getAuthHeaders("admin@crm.local", "admin123"));
        System.out.println(clientApiClient.getAllClients());
    }

 */

    protected Map<String, String> getAuthHeaders(String email, String password){
        var loginRequest = new LoginRequest()
                .setEmail(email)
                .setPassword(password);
//        String accessToken = authApiClient.login(loginRequest).accessToken();
        String accessToken = httpController.sendRequest("http://localhost:8080/api/auth/login", HttpController.HttpMethod.POST, null, loginRequest, ContentType.JSON)
                .extract().as(LoginResponse.class).accessToken();
        return Map.of("Authorization", "Bearer " + accessToken);
    }
}
