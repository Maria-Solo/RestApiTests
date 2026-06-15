package com.mary.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mary.BaseTest;
import com.mary.client.ClientApiClient;
import com.mary.fixtures.ClientFixture;
import com.mary.models.Client;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTests extends BaseTest {
    private static final Log log = LogFactory.getLog(ClientTests.class);
    ClientApiClient client = new ClientApiClient();
    ClientFixture fixture = new ClientFixture();

    private Map<String, String> getHeaders(String email, String password) {
        return getAuthHeaders(email, password);
    }

    @Test
    @DisplayName("Get client by id with schema validation")
    public void shouldGetClientByIdWithSchemaValidation() throws JsonProcessingException {
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var response = client.getClientByIdAsJson(headers, 1L);
        assertThat(response, matchesJsonSchemaInClasspath("schemas/client-schema.json"));
    }

    @Test
    void shouldGetAllClients(){
    var email = "admin@crm.local";
    var password = "admin123";
    var headers = getHeaders(email, password);
    var response = client.getAllClients(headers);

    assertEquals(6, response.size(), "Size of response is not equal to expected");
    };

    @Test
    void shouldGetClientById(){
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var response = client.getClientById(headers, 1L);

        assertEquals("John Doe", response.getName());
    };

    @Test
    void shouldCreateClient(){

        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var newClient = fixture.buildClient("James", "james@mail.test", "+12223334567", "New Company");
        var response = client.createClient(headers, newClient);
        assertEquals(newClient.getName(), response.getName());
        assertEquals(newClient.getEmail(), response.getEmail());
        assertEquals(newClient.getPhone(), response.getPhone());
        assertEquals(newClient.getCompany(), response.getCompany());
    }

    @Test
    void shouldUpdateClient(){

        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        var newClient = fixture.validClient();
        var response = client.updateClient(headers, 11L, newClient);
        assertEquals(newClient.getName(), response.getName());
        assertEquals(newClient.getEmail(), response.getEmail());
        assertEquals(newClient.getPhone(), response.getPhone());
        assertEquals(newClient.getCompany(), response.getCompany());
    }

    @Test
    void shouldDeleteClient(){
        var email = "admin@crm.local";
        var password = "admin123";
        var headers = getHeaders(email, password);
        client.deleteClient(headers, 11L);
        assertThatThrownBy(() -> client.getClientById(headers, 11L));
        //var response = client.getAllClients(headers);
        //assertEquals(6, response.size(), "Size of response is not equal to expected");
    }

}

