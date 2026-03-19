package com.mary.client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

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
}
