package com.mary.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mary.models.Client;
import com.mary.models.Task;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class TaskApiClient {
    public Response getAll() {
        return given()
                .when()
                .get("/tasks");
    }

    public Response getById(int id) {
        return given()
                .when()
                .get("/tasks/" + id);
    }

    public Response create(String body) {
        return given()
                .body(body)
                .when()
                .post("/tasks");
    }

    public Response update(String body, int id) {
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

    public List<Task> getAllTasks() {
        Response response = given()
                .when()
                .get("/tasks");
        response.then().statusCode(200);
        return Arrays.asList(response.as(Task[].class));
    }

    public Task getTaskById(Long id) {
        Response response = given()
                .when()
                .get("/tasks/" + id);
        response.then().statusCode(200);
        return response.as(Task.class);
    }

    public Task createTask(Task task) {
        Response response = given()
                .body(task)
                .when()
                .post("/tasks");
        response.then().statusCode(201);
        return response.as(Task.class);
    }

    public Task updateTask(Long id, Task task) {
        Response response = given()
                .body(task)
                .when()
                .put("/tasks/" + id);
        response.then().statusCode(200);
        return response.as(Task.class);
    }

    public void deleteTask(Long id) {
        Response response = given()
                .when()
                .delete("/tasks/" + id);
        response.then().statusCode(204);
    }

}
