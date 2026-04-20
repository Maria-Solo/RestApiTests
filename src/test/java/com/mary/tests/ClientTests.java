package com.mary.tests;

import com.mary.BaseTest;
import com.mary.HttpController;
import com.mary.client.AuthApiClient;
import com.mary.client.ClientApiClient;
import com.mary.models.Client;
import com.mary.models.Provider;
import io.restassured.http.ContentType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.mary.specs.RequestSpec.requestSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.setAllowComparingPrivateFields;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTests extends BaseTest {
    private static final Log log = LogFactory.getLog(ClientTests.class);
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

    @Test
    void shouldGetAllClients(){
        List<Client> clients = client.getAllClients();
        assertThat(clients)
                .isNotEmpty();
    }

    @Test
    void shouldGetOneClientById(){
        Client foundClient = client.getClientById(1L);
        assertThat(foundClient.getId()).isEqualTo(1);
        assertThat(foundClient.getName()).isEqualTo("John Doe");
    }

    @Test
    void shouldCreateClient(){
        var newClient = new Client()
                .setName("Test")
                .setEmail("test@test.com")
                .setPhone("+1234561212")
                .setCompany("SSL");
        Client created = client.createClient(newClient);

        assertThat(created.getName()).isEqualTo("Test");
        assertThat(created.getEmail()).isEqualTo("test@test.com");
        assertThat(created.getPhone()).isEqualTo("+1234561212");
        assertThat(created.getCompany()).isEqualTo("SSL");
        assertThat(created.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldUpdateClient(){
        var updatedClient = new Client()
                .setName("Test1")
                .setEmail("test1@test.com")
                .setPhone("+1234561213")
                .setCompany("SSL1");

        Client updated = client.updateClient(6L, updatedClient);
        assertThat(updated.getName()).isEqualTo("Test1");
        assertThat(updated.getEmail()).isEqualTo("test1@test.com");
        assertThat(updated.getPhone()).isEqualTo("+1234561213");
        assertThat(updated.getCompany()).isEqualTo("SSL1");
        assertThat(updated.getCreatedAt()).isNotNull();

        Client fetched = client.getClientById(6L);
        assertThat(fetched.getName()).isEqualTo("Test1");
        assertThat(fetched.getEmail()).isEqualTo("test1@test.com");
    }

    @Test
    void shouldDeleteClient(){
        // Создаем клиента для теста
        Client testClient = createTestClient();
        System.out.println("Клиент создан");

        // Удаляем его
        client.deleteClient(testClient.getId());
        System.out.println("Клиент удален");

        // Проверяем, что он действительно удален
        assertThatThrownBy(() -> client.getClientById(testClient.getId()))
                .isInstanceOf(AssertionError.class);
    }

    private Client createTestClient() {
        var newClient = new Client()
                .setName("Test Client")
                .setEmail("test" + System.currentTimeMillis() + "@test.com")
                .setPhone("+1234561299")
                .setCompany("SSLovable");

        return client.createClient(newClient);
    }

    @Test
    void shouldGetAllClients1(){
    var email = "admin@crm.local";
    var password = "admin123";
    var headers = getHeaders(email, password);
    var response = client.getAllClients1(headers);

    assertEquals(6, response.size(), "Size of response is not equal to expected");
    };

    private Map<String, String> getHeaders(String email, String password) {

    return getAuthHeaders(email, password);
}


    @Test
    void shouldGetClientById1(){
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var response = client.getClientById1(headers, 1L);

        assertEquals("John Doe", response.getName());
    };

    @Test
    void shouldCreateClient1(){

        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var newClient = new Client()
                .setName("Test")
                .setEmail("test@test.com")
                .setPhone("+1234561212")
                .setCompany("SSL");
        var response = client.createClient1(headers, newClient);
        assertEquals("Test", response.getName());
        assertEquals("test@test.com", response.getEmail());
        assertEquals("+1234561212", response.getPhone());
        assertEquals("SSL", response.getCompany());
    }

}

