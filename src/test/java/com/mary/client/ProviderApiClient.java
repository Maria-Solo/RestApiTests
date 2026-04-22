package com.mary.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mary.HttpController;
import com.mary.models.Client;
import com.mary.models.Provider;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;

public class ProviderApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/providers";
    ObjectMapper mapper = new ObjectMapper();

    HttpController httpController = new HttpController();
    private Map<String, String> headers;

    public Response getAll(){
        return given()
                .when()
                .get("/providers");
    }

    public Response getById(int id){
        return given()
                .when()
                .get("/providers/" + id);
    }

    public Response create(String body){
        return given()
                .body(body)
                .when()
                .post("/providers");
    }

    public Response update(String body, int id){
        return given()
                .body(body)
                .when()
                .put("/providers/" + id);
    }

    public Response delete(int id) {
        return given()
                .when()
                .delete("/providers/" + id);
    }

    public List<Provider> getAllProviders(){
        Response response = given()
                .when()
                .get("/providers");
        response.then().statusCode(200);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String jsonString = response.asString();
        try {
            return mapper.readValue(jsonString, new TypeReference<List<Provider>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize providers", e);
        }
    }

    public Provider getProviderById(int id) {
        Response response = given()
                .when()
                .get("/providers/" + id);
        response.then().statusCode(200);
        return response.as(Provider.class);
    }

    public Provider createProvider(Provider provider){
        Response response = given()
                .body(provider)
                .when()
                .post("/providers");
        response.then().statusCode(201);
        return response.as(Provider.class);
    }

    public Provider updateProvider(int id, Provider provider){
        Response response = given()
                .body(provider)
                .when()
                .put("/providers/" + id);
        response.then().statusCode(200);
        return response.as(Provider.class);
    }

    public void deleteProvider(int id){
        Response response = given()
                .when()
                .delete("/providers/" + id);
        response.then().statusCode(204);
    }

    public List<Provider> getAllProviders1(Map<String, String> headers) {
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

    public Provider getProviderById1(Map<String, String> headers, Long id) {
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

    public Provider createProvider1(Map<String, String> headers, Provider provider) {
        /*
        var response = httpController.sendRequest(BASE_URL, HttpController.HttpMethod.POST, headers, client, ContentType.JSON)
                .extract().response();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(response.asString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize clients", e);
        }

         */
        var response = httpController.sendRequest(BASE_URL, HttpController.HttpMethod.POST, headers, provider, ContentType.JSON)
                .extract().response();
        return response.as(Provider.class);
    }

    public Provider updateProvider1(Map<String, String> headers, Long id, Provider provider) {
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

    public void deleteProvider1(Map<String, String> headers, Long id) {
        httpController.sendRequest(BASE_URL + "/" + id, HttpController.HttpMethod.DELETE, headers, null, ContentType.ANY);
    }
}
