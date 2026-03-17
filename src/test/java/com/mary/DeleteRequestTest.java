package com.mary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.mary.specifcation.Specifications.requestSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class DeleteRequestTest {

    @Test
    @DisplayName("Удаление клиента")
    public void deleteClient(){
        given()
                .spec(requestSpec())
                .when()
                .delete("/clients/5")
                .then()
                .statusCode(204);
    }
    /// check cascade deleting (when we delet a client, their tasks also delete)

    @Test
    @DisplayName("Delete a task")
    public void deleteTask(){
        given()
                .spec(requestSpec())
                .when()
                .delete("/tasks/6")
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Delete a provider")
    public void deleteProvider(){
        given()
                .spec(requestSpec())
                .when()
                .delete("/providers/4")
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Каскадное удаление задач при удалении клиента")
    public void cascadeDeletingTasks(){
        String clientBody = """
                {
                "name": "New client",
                "email": "client@test.com",
                "phone": "+71234561234",
                "company": "SSL Solutions"
                }
                """;
        Integer clientId = given()
                .spec(requestSpec())
                .body(clientBody)
                .when()
                .post("/clients")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        String taskBody1 = """
            {
            "title": "Task 1",
            "description": "First task",
            "status": "NEW",
            "clientId": %d,
            "providerId": 2
            }
            """.formatted(clientId);

        String taskBody2 = """
            {
            "title": "Task 2",
            "description": "Second task",
            "status": "NEW",
            "clientId": %d,
            "providerId": 2
            }
            """.formatted(clientId);

        // Создаем первую задачу
        Integer taskId1 = given()
                .spec(requestSpec())
                .body(taskBody1)
                .when()
                .post("/tasks")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Создаем вторую задачу
        Integer taskId2 = given()
                .spec(requestSpec())
                .body(taskBody2)
                .when()
                .post("/tasks")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        given()
                .spec(requestSpec())
                .when()
                .delete("/clients/" + clientId)
                .then()
                .statusCode(204);

        // 4. Проверяем, что задачи больше не существуют (должны вернуть 404)
        given()
                .spec(requestSpec())
                .when()
                .get("/tasks/" + taskId1)
                .then()
                .statusCode(404);

        given()
                .spec(requestSpec())
                .when()
                .get("/tasks/" + taskId2)
                .then()
                .statusCode(404);
    }


}
