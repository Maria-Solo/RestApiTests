package com.mary.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mary.HttpController;
import com.mary.models.Task;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TaskApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/tasks";
    ObjectMapper mapper = new ObjectMapper();

    HttpController httpController = new HttpController();

    public Response getById(int id) {
        return given()
                .when()
                .get("/tasks/" + id);
    }

    public List<Task> getAllTasks(Map<String, String> headers) {
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

    public Task getTaskById(Map<String, String> headers, Long id) {
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

    public String getTaskByIdAsJson(Map<String, String> headers, Long id) {
        var response = httpController.sendRequest(BASE_URL + "/" + id, HttpController.HttpMethod.GET, headers, null, ContentType.ANY)
                .extract().response();
        return response.asString();
    }

    public Task createTask(Map<String, String> headers, Task task) {
        var response = httpController.sendRequest(BASE_URL, HttpController.HttpMethod.POST, headers, task, ContentType.JSON)
                .extract().response();
        return response.as(Task.class);
    }

    public Task updateTask(Map<String, String> headers, Long id, Task task) {
        var response = httpController.sendRequest(BASE_URL + "/" + id, HttpController.HttpMethod.PUT, headers, task, ContentType.JSON)
                .extract().response();
        return response.as(Task.class);
    }

    public void deleteTask(Map<String, String> headers, Long id) {
        httpController.sendRequest(BASE_URL + "/" + id, HttpController.HttpMethod.DELETE, headers, null, ContentType.ANY);
    }

}
