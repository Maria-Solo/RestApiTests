package com.mary.client;

import com.mary.models.Provider;
import io.restassured.response.Response;
import org.codehaus.groovy.control.io.ReaderSource;

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
        return response.jsonPath().getList("", Provider.class);
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
