package com.mary.tests;

import com.mary.BaseTest;
import com.mary.client.ProviderApiClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.mary.specs.RequestSpec.requestSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class ProviderTests extends BaseTest {

    ProviderApiClient provider = new ProviderApiClient();

    @Test
    @DisplayName("Список провайдеров с проверкой статуса и размера массива")
    public void getAllProviders(){
       provider.getAll()
                .then()
                .statusCode(200)
                .body("$", hasSize(3));
    }

    @Test
    @DisplayName("Получить провайдера по айди")
    public void getProviderById(){
        provider.getById(1)
                .then()
                .statusCode(200)
                .body("name", equalTo("Cloud Provider"))
                .body("id", equalTo(1));
    }

    @Test
    @DisplayName("Create a provider")
    public void createProvider(){

        String body = """
    {
      "name": "New Provider",
      "email": "cloud@example.com",
      "phone": "+9999999999",
      "serviceType": "CLOUD"
    }
    """;
        provider.create(body)
                .then()
                .statusCode(201)
                .body("name", equalTo("New Provider"))
                .body("serviceType", equalTo("CLOUD"));
    }

    @Test
    @DisplayName("Update a provider")
    public void updateProvider(){

        String body = """
    {
      "name": "New Provider",
      "email": "cloud123@example.com",
      "phone": "+9999999900",
      "serviceType": "CLOUDs"
    }
    """;
provider.update(body, 5)
                .then()
                .statusCode(200)
                .body("name", equalTo("New Provider"))
                .body("email", equalTo("cloud123@example.com"))
                .body("phone", equalTo("+9999999900"))
                .body("serviceType", equalTo("CLOUDs"));
    }

    @Test
    @DisplayName("Delete a provider")
    public void deleteProvider(){
        provider.delete(5)
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Get a provider with schema validation")
    public void shouldGetProviderByIdWithSchemaValidation(){
        provider.getById(1)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/provider-schema.json"));
    }
}
