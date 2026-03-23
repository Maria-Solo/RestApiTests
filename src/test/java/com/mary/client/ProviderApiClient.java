package com.mary.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mary.models.Provider;
import io.restassured.response.Response;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;

public class ProviderApiClient {
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
}
