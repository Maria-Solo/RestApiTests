package com.mary.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mary.BaseTest;
import com.mary.client.TaskApiClient;
import com.mary.fixtures.TaskFixture;
import com.mary.models.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTests extends BaseTest {

    TaskApiClient task = new TaskApiClient();
    private Map<String, String> getHeaders(String email, String password) {
        return getAuthHeaders(email, password);
    }
    TaskFixture fixture = new TaskFixture();

    @Test
    @DisplayName("Get task by id with schema validation")
    public void shouldGetTaskByIdWithSchemaValidation() throws JsonProcessingException {
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var response = task.getTaskByIdAsJson(headers, 1L);
        assertThat(response, matchesJsonSchemaInClasspath("schemas/task-schema.json"));
    }

    @Test
    void shouldGetAllTasks(){
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var response = task.getAllTasks(headers);
        assertEquals(9, response.size(), "Size of response is equal to expected");
    }

    @Test
    void shouldGetTaskById(){
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var response = task.getTaskById(headers, 1L);

        assertEquals("Setup cloud infrastructure", response.getTitle());
    }

    @Test
    void shouldGetCreateTask() {
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var newTask = fixture.buildTask("The test task", "Test task description", "NEW", 2L, 2L);
        var response = task.createTask(headers, newTask);
        assertEquals(newTask.getTitle(), response.getTitle());
        assertEquals(newTask.getDescription(), response.getDescription());
        assertEquals(newTask.getStatus(), response.getStatus());
        assertEquals(newTask.getClientId(), response.getClient().getId());
    }

    @Test
    void shouldUpdateTask(){
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var updatedTask = fixture.validTask();
        var response = task.updateTask(headers, 14L, updatedTask);
        assertEquals(updatedTask.getTitle(), response.getTitle());
        assertEquals(updatedTask.getDescription(), response.getDescription());
        assertEquals(updatedTask.getStatus(), response.getStatus());
        assertEquals(updatedTask.getClientId(), response.getClient().getId());
    }

    @Test
    void shouldDeleteTask(){
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        task.deleteTask(headers, 14L);
        assertThatThrownBy(() -> task.getTaskById(headers, 14L));
    }
}
