package com.mary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static com.mary.specs.RequestSpec.requestSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PostRequestTest {

    @Test
    @DisplayName("Добавление клиента")
    public void createClient(){
        String body = """
                {
                "name": "Masha",
                "email": "masha@test.com",
                "phone": "+71234561234",
                "company": "SSL Solutions"
                }
                """;
        given()
                .spec(requestSpec())
                .body(body)
                .when()
                .post("/clients")
                .then()
                .statusCode(201)
                .body("name", equalTo("Masha"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    @DisplayName("Creating a task")
    public void createTask() {
        String body = """
                {
                "title": "Test task",
                       "description": "Testing api",
                       "status": "NEW",
                       "clientId": 5,
                       "providerId": 2
                }
                """;
        given()
                .spec(requestSpec())
                .body(body)
                .when()
                .post("/tasks")
                .then()
                .statusCode(201)
                .body("title", equalTo("Test task"))
                .body("client.id", equalTo(5))
                .body("provider.id", equalTo(2));
    }

    @Test
    @DisplayName("Create a provider")
    public void createProvider(){

        String body = """
    {
      "name": "Cloud Provider",
      "email": "cloud@example.com",
      "phone": "+9999999999",
      "serviceType": "CLOUD"
    }
    """;

        given()
                .spec(requestSpec())
                .body(body)
                .when()
                .post("/providers")
                .then()
                .statusCode(201)
                .body("name", equalTo("Cloud Provider"))
                .body("serviceType", equalTo("CLOUD"));
    }



}
