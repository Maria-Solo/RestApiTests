package com.mary.tests;

import com.mary.BaseTest;
import com.mary.client.ProviderApiClient;
import com.mary.models.Provider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProviderTests extends BaseTest {

    ProviderApiClient clientProvider = new ProviderApiClient();

    private Map<String, String> getHeaders(String email, String password) {
        return getAuthHeaders(email, password);
    }

    @Test
    @DisplayName("Get a provider with schema validation")
    public void shouldGetProviderByIdWithSchemaValidation() {
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var response = clientProvider.getProviderByIdAsJson(headers, 1L);
        assertThat(response, matchesJsonSchemaInClasspath("schemas/provider-schema.json"));
    }

    @Test
    void shouldGetAllProviders(){
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var response = clientProvider.getAllProviders(headers);
        assertEquals(6, response.size(), "Size of response is equal to expected");
    };

    @Test
    void shouldGetProviderById(){
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var response = clientProvider.getProviderById(headers, 1L);

        assertEquals("Cloud Provider", response.getName());
    };

    @Test
    void shouldCreateProvider(){

        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var newProvider = new Provider()
                .setName("New Provider")
                .setEmail("test@provider.com")
                .setPhone("+1234567890")
                .setServiceType(Provider.ServiceType.SECURITY);
        var response = clientProvider.createProvider(headers, newProvider);
        assertEquals(newProvider.getName(), response.getName());
        assertEquals(newProvider.getEmail(), response.getEmail());
        assertEquals(newProvider.getPhone(), response.getPhone());
    }

    @Test
    void shouldUpdateProvider(){

        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var newProvider = new Provider()
                .setName("New Provider1")
                .setEmail("test1@provider.com")
                .setPhone("+1234567891")
                .setServiceType(Provider.ServiceType.SECURITY);
        var response = clientProvider.updateProvider(headers, 7L, newProvider);
        assertEquals(newProvider.getName(), response.getName());
        assertEquals(newProvider.getEmail(), response.getEmail());
        assertEquals(newProvider.getPhone(), response.getPhone());
    }

    @Test
    void shouldDeleteProvider(){
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        clientProvider.deleteProvider(headers, 11L);
        var response = clientProvider.getAllProviders(headers);
        assertEquals(5, response.size(), "Size of response is equal to expected");
    }

}
