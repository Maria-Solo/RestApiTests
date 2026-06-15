package com.mary.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mary.HttpController;
import com.mary.models.Provider;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ProviderApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/providers";
    ObjectMapper mapper = new ObjectMapper();

    HttpController httpController = new HttpController();

    public Response getById(int id){
        return given()
                .when()
                .get("/providers/" + id);
    }

    public List<Provider> getAllProviders(Map<String, String> headers) {
        var response = httpController.sendRequest(BASE_URL, HttpController.HttpMethod.GET, headers, null, ContentType.ANY)
                .extract().response();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(response.asString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize providers", e);
        }
    }

    public Provider getProviderById(Map<String, String> headers, Long id) {
        var response = httpController.sendRequest(BASE_URL + "/" + id, HttpController.HttpMethod.GET, headers, null, ContentType.ANY)
                .extract().response();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(response.asString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize provider", e);
        }
    }

    public String getProviderByIdAsJson(Map<String, String> headers, Long id) {
        var response = httpController.sendRequest(BASE_URL + "/" + id, HttpController.HttpMethod.GET, headers, null, ContentType.ANY)
                .extract().response();
        return response.asString();
    }

    public Provider createProvider(Map<String, String> headers, Provider provider) {
        var response = httpController.sendRequest(BASE_URL, HttpController.HttpMethod.POST, headers, provider, ContentType.JSON)
                .extract().response();
        return response.as(Provider.class);
    }

    public Provider updateProvider(Map<String, String> headers, Long id, Provider provider) {
        var response = httpController.sendRequest(BASE_URL + "/" + id, HttpController.HttpMethod.PUT, headers, provider, ContentType.JSON)
                .extract().response();
        //это надо?
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(response.asString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize provider", e);
        }
    }

    public void deleteProvider(Map<String, String> headers, Long id) {
        httpController.sendRequest(BASE_URL + "/" + id, HttpController.HttpMethod.DELETE, headers, null, ContentType.ANY);
    }
}
