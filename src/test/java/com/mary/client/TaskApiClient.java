package com.mary.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mary.HttpController;
import com.mary.models.Client;
import com.mary.models.Provider;
import com.mary.models.Task;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TaskApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/tasks";
    ObjectMapper mapper = new ObjectMapper();

    HttpController httpController = new HttpController();

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

    public List<Task> getAllTasks1(Map<String, String> headers) {
        var response = httpController.sendRequest(BASE_URL, HttpController.HttpMethod.GET, headers, null, ContentType.ANY)
                .extract().response();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(response.asString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize tasks", e);
        }
    }

    public Task getTaskById1(Map<String, String> headers, Long id) {
        var response = httpController.sendRequest(BASE_URL + "/" + id, HttpController.HttpMethod.GET, headers, null, ContentType.ANY)
                .extract().response();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(response.asString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize task", e);
        }
    }

    public Task createTask1(Map<String, String> headers, Task task) {
        var response = httpController.sendRequest(BASE_URL, HttpController.HttpMethod.POST, headers, task, ContentType.JSON)
                .extract().response();
        return response.as(Task.class);
    }

    public Task updateTask1(Map<String, String> headers, Long id, Task task) {
        var response = httpController.sendRequest(BASE_URL + "/" + id, HttpController.HttpMethod.PUT, headers, task, ContentType.JSON)
                .extract().response();
        return response.as(Task.class);
    }

    public void deleteTask1(Map<String, String> headers, Long id) {
        httpController.sendRequest(BASE_URL + "/" + id, HttpController.HttpMethod.DELETE, headers, null, ContentType.ANY);
    }

}
