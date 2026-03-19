package com.mary.client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TaskApiClient {
    public Response getAll(){
        return given()
                .when()
                .get("/tasks");
    }

    public Response getById(int id){
        return given()
                .when()
                .get("/tasks/" + id);
    }

    public Response create(String body){
        return given()
                .body(body)
                .when()
                .post("/tasks");
    }

    public Response update(String body, int id){
        return given()
                .body(body)
                .when()
                .put("/tasks/" + id);
    }

    public Response delete(int id) {
        return given()
                .when()
                .delete("/tasks/" + id);
    }
}
