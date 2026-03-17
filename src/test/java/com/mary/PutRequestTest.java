package com.mary;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.mary.specifcation.Specifications.requestSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PutRequestTest {

    @Test
    @DisplayName("Обновление клиента")
    public void updateClient(){
        String body = """
                {
                "name": "Masha",
                "email": "masha1@test.com",
                "phone": "+71234561235",
                "company": "SSL Solutions"
                }
                """;
        given()
                .spec(requestSpec())
                .body(body)
                .when()
                .put("/clients/4")
                .then()
                .statusCode(200)
                .body("email", equalTo("masha1@test.com"))
                .body("phone", equalTo("+71234561235"));
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
        given()
                .spec(requestSpec())
                .body(body)
                .when()
                .put("/tasks/6")
                .then()
                .statusCode(200)
                .body("title", equalTo("Test task123"))
                .body("description", equalTo("Testing api123"))
                .body("status", equalTo("IN_PROGRESS"));
    }

    @Test
    @DisplayName("Update a provider")
    public void updateProvider(){

        String body = """
    {
      "name": "Cloud Provider",
      "email": "cloud123@example.com",
      "phone": "+9999999900",
      "serviceType": "CLOUDs"
    }
    """;

        given()
                .spec(requestSpec())
                .body(body)
                .when()
                .put("/providers/4")
                .then()
                .statusCode(200)
                .body("name", equalTo("Cloud Provider"))
                .body("email", equalTo("cloud123@example.com"))
                .body("phone", equalTo("+9999999900"))
                .body("serviceType", equalTo("CLOUDs"));
    }

    @Test
    @DisplayName("Обновление клиента с использованием заголовков")
    public void updateClientHeaders(){
        String body = """
                {
                "name": "Masha",
                "email": "masha1@test.com",
                "phone": "+71234561235",
                "company": "SSL Solutions"
                }
                """;
        given()
                //.spec(requestSpec())
                .baseUri("http://localhost:8080")
                .basePath("/api")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(body)
                .when()
                .put("/clients/7")
                .then()
                .statusCode(200)
                .body("email", equalTo("masha1@test.com"))
                .body("phone", equalTo("+71234561235"))
                .header("Content-Type", containsString("application/json"));
    }
}
