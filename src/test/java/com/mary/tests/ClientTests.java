package com.mary.tests;

import com.mary.BaseTest;
import com.mary.client.ClientApiClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.mary.specs.RequestSpec.requestSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class ClientTests extends BaseTest {
    ClientApiClient client = new ClientApiClient();

    @Test
    @DisplayName("Get-запрос за списком клиентов с проверкой статус-кода и размера списка")
    public void getAllClients(){
        client.getAll()
                .then()
                .statusCode(200)
                .body("$", hasSize(4));
    }

    @Test
    @DisplayName("Get-запрос за одним клиентом c проверкой status code = 200 и данных клиента")
    public void getClientById() {
        client.getById(1)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("John Doe"))
                .body("company", equalTo("Acme Corp"));
    }

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
        client.create(body)
                .then()
                .statusCode(201)
                .body("name", equalTo("Masha"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

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
        client.update(body, 8)
                .then()
                .statusCode(200)
                .body("email", equalTo("masha1@test.com"))
                .body("phone", equalTo("+71234561235"));
    }

    @Test
    @DisplayName("Удаление клиента")
    public void deleteClient(){
       client.delete(8)
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Get client by id with schema validation")
    public void shouldGetClientByIdWithSchemaValidation() {
        client.getById(1)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/client-schema.json"));
    }


}
