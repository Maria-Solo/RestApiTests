package com.mary.tests;

import com.mary.BaseTest;
import com.mary.client.AuthApiClient;
import com.mary.client.ProviderApiClient;
import com.mary.models.Client;
import com.mary.models.Provider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProviderTests extends BaseTest {

    ProviderApiClient clientProvider = new ProviderApiClient();


    @Test
    @DisplayName("Список провайдеров с проверкой статуса и размера массива")
    public void getAllProviders() {
        clientProvider.getAll()
                .then()
                .statusCode(200)
                .body("$", hasSize(3));
    }

    @Test
    @DisplayName("Получить провайдера по айди")
    public void getProviderById() {
        clientProvider.getById(1)
                .then()
                .statusCode(200)
                .body("name", equalTo("Cloud Provider"))
                .body("id", equalTo(1));
    }

    @Test
    @DisplayName("Create a provider")
    public void createProvider() {

        String body = """
                {
                  "name": "New Provider",
                  "email": "cloud@example.com",
                  "phone": "+9999999999",
                  "serviceType": "CLOUD"
                }
                """;
        clientProvider.create(body)
                .then()
                .statusCode(201)
                .body("name", equalTo("New Provider"))
                .body("serviceType", equalTo("CLOUD"));
    }

    @Test
    @DisplayName("Update a provider")
    public void updateProvider() {

        String body = """
                {
                  "name": "New Provider",
                  "email": "cloud123@example.com",
                  "phone": "+9999999900",
                  "serviceType": "CLOUDs"
                }
                """;
        clientProvider.update(body, 5)
                .then()
                .statusCode(200)
                .body("name", equalTo("New Provider"))
                .body("email", equalTo("cloud123@example.com"))
                .body("phone", equalTo("+9999999900"))
                .body("serviceType", equalTo("CLOUDs"));
    }

    @Test
    @DisplayName("Delete a provider")
    public void deleteProvider() {
        clientProvider.delete(4)
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Get a provider with schema validation")
    public void shouldGetProviderByIdWithSchemaValidation() {
        clientProvider.getById(1)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/provider-schema.json"));
    }

    @Test
    void shouldGetAllProviders() {
        List<Provider> providers = clientProvider.getAllProviders();

        assertThat(providers)
                .isNotEmpty()
                .allMatch(provider -> provider.getId() > 0)
                .allMatch(provider -> provider.getName() != null);
    }

    @Test
    void shouldGetOneProviderById() {
        Provider foundProvider = clientProvider.getProviderById(1);
        assertThat(foundProvider.getId()).isEqualTo(1);
        assertThat(foundProvider.getName()).isEqualTo("Cloud Provider");
    }

    @Test
    void shouldCreateProvider() {
        var provider = new Provider()
                .setName("New Provider")
                .setEmail("test@provider.com")
                .setPhone("+1234567890")
                .setServiceType(Provider.ServiceType.SECURITY);

        Provider created = clientProvider.createProvider(provider);
        assertThat(created.getId()).isNotNull();
        assertThat(created.getName()).isEqualTo("New Provider");
        assertThat(created.getEmail()).isEqualTo("test@provider.com");
        assertThat(created.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldUpdateProvider() {
        var updatedProvider = new Provider()
                .setName("Updated Provider")
                .setEmail("update@provider.com")
                .setPhone("+1234567891")
                .setServiceType(Provider.ServiceType.SECURITY);

        Provider updated = clientProvider.updateProvider(6, updatedProvider);

        assertThat(updated.getId()).isEqualTo(6);
        assertThat(updated.getName()).isEqualTo("Updated Provider");
        assertThat(updated.getEmail()).isEqualTo("update@provider.com");
        assertThat(updated.getPhone()).isEqualTo("+1234567891");          // ← добавил проверку телефона
        assertThat(updated.getServiceType()).isEqualTo(Provider.ServiceType.SECURITY);
        assertThat(updated.getCreatedAt()).isNotNull();

        Provider fetched = clientProvider.getProviderById(6);
        assertThat(fetched.getName()).isEqualTo("Updated Provider");
        assertThat(fetched.getEmail()).isEqualTo("update@provider.com");
    }

    @Test
    void shouldDeleteProvider() {
        // Создаем провайдера для теста
        Provider testProvider = createTestProvider();
        System.out.println("Провайдер создан");

        // Удаляем его
        clientProvider.deleteProvider(Math.toIntExact(testProvider.getId()));
        System.out.println("Провайдер удален");

        // Проверяем, что он действительно удален
        assertThatThrownBy(() -> clientProvider.getProviderById(Math.toIntExact(testProvider.getId())))
                .isInstanceOf(AssertionError.class);
    }

    private Provider createTestProvider() {
        var newProvider = new Provider()
                .setName("Test Provider " + System.currentTimeMillis())
                .setEmail("test" + System.currentTimeMillis() + "@test.com")
                .setServiceType(Provider.ServiceType.CLOUD);

        return clientProvider.createProvider(newProvider);
    }

    @Test
    void shouldGetAllProviders1(){
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var response = clientProvider.getAllProviders1(headers);
        assertEquals(3, response.size(), "Size of response is not equal to expected");
    };

    private Map<String, String> getHeaders(String email, String password) {

        return getAuthHeaders(email, password);
    }


    @Test
    void shouldGetProviderById1(){
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var response = clientProvider.getProviderById1(headers, 1L);

        assertEquals("Cloud Provider", response.getName());
    };

    @Test
    void shouldCreateProvider1(){

        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var newProvider = new Provider()
                .setName("New Provider")
                .setEmail("test@provider.com")
                .setPhone("+1234567890")
                .setServiceType(Provider.ServiceType.SECURITY);
        var response = clientProvider.createProvider1(headers, newProvider);
        assertEquals("New Provider", response.getName());
        assertEquals("test@provider.com", response.getEmail());
        assertEquals("+1234567890", response.getPhone());
    }

    @Test
    void shouldUpdateProvider1(){

        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var newProvider = new Provider()
                .setName("New Provider1")
                .setEmail("test1@provider.com")
                .setPhone("+1234567891")
                .setServiceType(Provider.ServiceType.SECURITY);
        var response = clientProvider.updateProvider1(headers, 4L, newProvider);
        assertEquals("New Provider1", response.getName());
        assertEquals("test1@provider.com", response.getEmail());
        assertEquals("+1234567891", response.getPhone());
    }

    @Test
    void shouldDeleteProvider1(){
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        clientProvider.deleteProvider1(headers, 4L);

        var response = clientProvider.getAllProviders1(headers);
        assertEquals(3, response.size(), "Size of response is not equal to expected");
    }

}
