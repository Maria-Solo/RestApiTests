package com.mary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static com.mary.specifcation.Specifications.requestSpec;
import static org.hamcrest.Matchers.*;

public class GetRequestTest {

    public GetRequestTest(){};

    //GET /api/clients/1 - Проверить: статус-код, id, name, company
    @Test
    @DisplayName("Get-запрос за одним клиентом c проверкой status code = 200 и данных клиента")
    public void getRequestCheckStatusCode() {
                given()
                .spec(requestSpec())//---> Указание RequestSpecification для формирования request
                        .when()
                .get("/clients/1")//---> Endpoint для выполнения запроса GET
                .then()
                        .log().all()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("John Doe"))
                .body("company", equalTo("Acme Corp"));
    }


    @Test
    @DisplayName("Посмотреть данные в БД через API")
    public void printDatabaseContents() {
        String response = given()
                .spec(requestSpec())
                .when()
                .get("/clients")  // получаем всех клиентов
                .then()
                .statusCode(200)
                .extract()
                .response()
                .asString();

        System.out.println("Данные из БД:");
        System.out.println(response);
    }

    // GET /api/clients
    // GET /api/providers
    // GET /api/tasks
    // Проверить: статус-код, размер списка (seed-данные: 3 клиента, 3 провайдера, 5 задач)
    @Test
    @DisplayName("Get-запрос за списком клиентов с проверкой статус-кода и размера списка")
    public void getAllClients(){
        given()
                .spec(requestSpec())
                .when()
                .get("/clients")
                .then()
                .statusCode(200)
                .body("$", hasSize(3));
    }

    @Test
    @DisplayName("Список провайдеров с проверкой статуса и размера массива")
    public void getAllProviders(){
        given()
                .spec(requestSpec())
                .when()
                .get("/providers")
                .then()
                .statusCode(200)
                .body("$", hasSize(3));
    }

    @Test
    @DisplayName("Список задач с проверкой статуса и размера массива")
    public void getAllTasks(){
        given()
                .spec(requestSpec())
                .when()
                .get("/tasks")
                .then()
                .statusCode(200)
                .body("$", hasSize(5));
    }

    //GET /api/tasks/2
    //Проверить: статус-код, title, вложенное имя клиента, вложенное имя провайдера

    @Test
    @DisplayName("Получение одной задачи с проверкой статус-кода, названия, клиента и провайдера")
    public void getOneTaskWithDetails(){
        given()
                .spec(requestSpec())
                .when()
                .get("/tasks/2")
                .then()
                .statusCode(200)
                .body("title", equalTo("Security audit"))
                .body("client.name", equalTo("John Doe"))
                .body("provider.name", equalTo("Security Solutions"));
    }
}

