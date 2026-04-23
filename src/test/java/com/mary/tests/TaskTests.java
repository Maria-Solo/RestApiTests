package com.mary.tests;

import com.mary.BaseTest;
import com.mary.client.AuthApiClient;
import com.mary.client.TaskApiClient;
import com.mary.models.Client;
import com.mary.models.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.mary.specs.RequestSpec.requestSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void shouldGetAllTasks(){
        List<Task> tasks = task.getAllTasks();
        assertThat(tasks)
                .isNotEmpty();
    }

    @Test
    void shouldGetOneTaskById(){
        Task foundTask = task.getTaskById(1L);
        assertThat(foundTask.getId()).isEqualTo(1);
        assertThat(foundTask.getTitle()).isEqualTo("Setup cloud infrastructure");
    }

    @Test
    void shouldCreateTask(){
        var newTask = new Task()
                .setTitle("Test")
                .setDescription("testing")
                .setStatus("IN PROGRESS")
                .setClientId(1L)
                .setProviderId(1L);
        Task created = task.createTask(newTask);

        assertThat(created.getTitle()).isEqualTo("Test");
        assertThat(created.getDescription()).isEqualTo("testing");
        assertThat(created.getStatus()).isEqualTo("IN PROGRESS");
        assertThat(created.getClient().getId()).isEqualTo(1L);
        assertThat(created.getProvider().getId()).isEqualTo(1L);
    }

    @Test
    void shouldUpdateTask(){
        var updatedTask = new Task()
                .setTitle("Test1")
                .setDescription("testing1")
                .setStatus("DONE")
                .setClientId(1L)
                .setProviderId(1L);
        Task updated = task.updateTask(7l, updatedTask);

        assertThat(updated.getTitle()).isEqualTo("Test1");
        assertThat(updated.getDescription()).isEqualTo("testing1");
        assertThat(updated.getStatus()).isEqualTo("DONE");
        assertThat(updated.getClient().getId()).isEqualTo(1L);
        assertThat(updated.getProvider().getId()).isEqualTo(1L);
    }

    @Test
    void shouldDeleteTask(){
        var deleteTask = new Task()
                .setTitle("Test deleting")
                .setDescription("deleting")
                .setStatus("DONE")
                .setClientId(1L)
                .setProviderId(1L);
        Task deletingTask = task.createTask(deleteTask);

        task.delete(Math.toIntExact(deletingTask.getId()));

        assertThatThrownBy(() -> task.getTaskById(deletingTask.getId()))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void shouldGetAllTasks1(){
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var response = task.getAllTasks1(headers);
        assertEquals(7, response.size(), "Size of response is not equal to expected");
    }

    private Map<String, String> getHeaders(String email, String password) {
        return getAuthHeaders(email, password);
    }

    @Test
    void shouldGetTaskById1(){
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var response = task.getTaskById1(headers, 1L);

        assertEquals("Setup cloud infrastructure", response.getTitle());
    }

    @Test
    void shouldGetCreateTask() {
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var newTask = new Task()
                .setTitle("Test")
                .setDescription("testing")
                .setStatus("IN PROGRESS")
                .setClientId(1L)
                .setProviderId(1L);
        var response = task.createTask1(headers, newTask);
        assertEquals(newTask.getTitle(), response.getTitle());
        assertEquals(newTask.getDescription(), response.getDescription());
        assertEquals(newTask.getStatus(), response.getStatus());
        assertEquals(newTask.getClientId(), response.getClient().getId());
    }

    @Test
    void shouldUpdateTask1(){
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var updatedTask = new Task()
                .setTitle("Test3")
                .setDescription("testing3")
                .setStatus("DONE")
                .setClientId(2L)
                .setProviderId(2L);
        var response = task.updateTask1(headers, 12L, updatedTask);
        assertEquals(updatedTask.getTitle(), response.getTitle());
        assertEquals(updatedTask.getDescription(), response.getDescription());
        assertEquals(updatedTask.getStatus(), response.getStatus());
        assertEquals(updatedTask.getClientId(), response.getClient().getId());
    }

    @Test
    void shouldDeleteTask1(){
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        task.deleteTask1(headers, 12L);
        assertThatThrownBy(() -> task.getTaskById1(headers, 12L));
            //    .isInstanceOf(NotFoundException.class);
    }
}
