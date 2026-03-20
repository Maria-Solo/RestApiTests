package com.mary.tests;

import com.mary.BaseTest;
import com.mary.client.ProviderApiClient;
import com.mary.models.Provider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.mary.specs.RequestSpec.requestSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Fail.fail;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
public class ProviderTests extends BaseTest {

    ProviderApiClient provider = new ProviderApiClient();

    @Test
    @DisplayName("Список провайдеров с проверкой статуса и размера массива")
    public void getAllProviders() {
        provider.getAll()
                .then()
                .statusCode(200)
                .body("$", hasSize(3));
    }

    @Test
    @DisplayName("Получить провайдера по айди")
    public void getProviderById() {
        provider.getById(1)
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
        provider.create(body)
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
    public void deleteProvider() {
        provider.delete(5)
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Get a provider with schema validation")
    public void shouldGetProviderByIdWithSchemaValidation() {
        provider.getById(1)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/provider-schema.json"));
    }

    @Test
    void shouldGetAllProviders() {
        List<Provider> providers = provider.getAllProviders();

        assertThat(providers)
                .isNotEmpty()
                .allMatch(provider -> provider.getId() > 0)
                .allMatch(provider -> provider.getName() != null);
    }

    @Test
    void shouldGetOneProviderById() {
        Provider foundProvider = provider.getProviderById(1);
        assertThat(foundProvider.getId()).isEqualTo(1);
        assertThat(foundProvider.getName()).isEqualTo("Cloud Provider");
    }

    @Test
    void shouldCreateProvider() {
        Provider newProvider = Provider.builder()
                .name("New Provider")
                .email("test@provider.com")
                .phone("+1234567890")
                .serviceType(Provider.ServiceType.SECURITY)
                .build();

        Provider created = provider.createProvider(newProvider);
        assertThat(created.getId()).isNotNull();
        assertThat(created.getName()).isEqualTo("New Provider");
        assertThat(created.getEmail()).isEqualTo("test@provider.com");
        assertThat(created.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldUpdateProvider() {
        Provider updatedProvider = Provider.builder()
                .name("Updated Provider")
                .email("update@provider.com")
                .phone("+1234567891")
                .serviceType(Provider.ServiceType.SECURITY)
                .build();

        Provider updated = provider.updateProvider(6, updatedProvider);

        assertThat(updated.getId()).isEqualTo(6);
        assertThat(updated.getName()).isEqualTo("Updated Provider");
        assertThat(updated.getEmail()).isEqualTo("update@provider.com");
        assertThat(updated.getPhone()).isEqualTo("+1234567891");          // ← добавил проверку телефона
        assertThat(updated.getServiceType()).isEqualTo(Provider.ServiceType.SECURITY);
        assertThat(updated.getCreatedAt()).isNotNull();

        Provider fetched = provider.getProviderById(6);
        assertThat(fetched.getName()).isEqualTo("Updated Provider");
        assertThat(fetched.getEmail()).isEqualTo("update@provider.com");
    }

    @Test
    void shouldDeleteProvider() {
        // Создаем провайдера для теста
        Provider testProvider = createTestProvider();

        // Удаляем его
        provider.deleteProvider(Math.toIntExact(testProvider.getId()));

        // Проверяем, что он действительно удален
        assertThatThrownBy(() -> provider.getProviderById(Math.toIntExact(testProvider.getId())))
                .isInstanceOf(Exception.class);
    }

    private Provider createTestProvider() {
        Provider newProvider = Provider.builder()
                .name("Test Provider " + System.currentTimeMillis())
                .email("test" + System.currentTimeMillis() + "@test.com")
                .serviceType(Provider.ServiceType.CLOUD)
                .build();

        return provider.createProvider(newProvider);
    }
}
