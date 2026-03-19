package com.mary.tests;

import com.mary.BaseTest;
import com.mary.client.TaskApiClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.mary.specs.RequestSpec.requestSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class TaskTests extends BaseTest {

    TaskApiClient task = new TaskApiClient();

    @Test
    @DisplayName("Список задач с проверкой статуса и размера массива")
    public void getAllTasks(){
       task.getAll()
                .then()
                .statusCode(200)
                .body("$", hasSize(6));
    }

    @Test
    @DisplayName("Получение одной задачи с проверкой статус-кода, названия, клиента и провайдера")
    public void getOneTaskWithDetails(){
        task.getById(2)
                .then()
                .statusCode(200)
                .body("title", equalTo("Security audit"))
                .body("client.name", equalTo("John Doe"))
                .body("provider.name", equalTo("Security Solutions"));
    }

    @Test
    @DisplayName("Creating a task")
    public void createTask() {
        String body = """
                {
                "title": "Test task",
                       "description": "Testing api",
                       "status": "NEW",
                       "clientId": 2,
                       "providerId": 2
                }
                """;
        task.create(body)
                .then()
                .statusCode(201)
                .body("title", equalTo("Test task"))
                .body("client.id", equalTo(2))
                .body("provider.id", equalTo(2));
    }

    @Test
    @DisplayName("Updating a task")
    public void updateTask() {
        String body = """
                {
                "title": "Test task123",
                       "description": "Testing api123",
                       "status": "IN_PROGRESS",
                       "clientId": 1,
                       "providerId": 2
                }
                """;
        task.update(body, 11)
                .then()
                .statusCode(200)
                .body("title", equalTo("Test task123"))
                .body("description", equalTo("Testing api123"))
                .body("status", equalTo("IN_PROGRESS"));
    }

    @Test
    @DisplayName("Delete a task")
    public void deleteTask(){
        task.delete(11)
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Get a task with schema validation")
    public void shouldGetTaskByIdWithSchemaValidation(){
        task.getById(1)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/task-schema.json"));
    }

}
