package com.mary.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mary.HttpController;
import com.mary.models.Client;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class ClientApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/clients";
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);;

    HttpController httpController = new HttpController();

    public Response getById(int id) {
        return given()
                .when()
                .get("/clients/" + id);
    }

    public List<Client> getAllClients(Map<String, String> headers) {
        var response = httpController.sendRequest(BASE_URL, HttpController.HttpMethod.GET, headers, null, ContentType.ANY)
                .extract().response();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(response.asString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize clients", e);
        }
    }

    public String getClientByIdAsJson(Map<String, String> headers, Long id) {
        var response = httpController.sendRequest(BASE_URL + "/" + id, HttpController.HttpMethod.GET, headers, null, ContentType.ANY)
                .extract().response();
        return response.asString();
    }

    public Client getClientById(Map<String, String> headers, Long id) {
        var response = httpController.sendRequest(BASE_URL + "/" + id, HttpController.HttpMethod.GET, headers, null, ContentType.ANY)
                .extract().response();

        try {
            return mapper.readValue(response.asString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize clients", e);
        }
    }

    public Client createClient(Map<String, String> headers, Client client) {
        var response = httpController.sendRequest(BASE_URL, HttpController.HttpMethod.POST, headers, client, ContentType.JSON)
                .extract().response();
        return response.as(Client.class);
    }

    public Client updateClient(Map<String, String> headers, Long id, Client client) {
        var response = httpController.sendRequest(BASE_URL + "/" + id, HttpController.HttpMethod.PUT, headers, client, ContentType.JSON)
                .extract().response();

        try {
            return mapper.readValue(response.asString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize clients", e);
        }
    }

    public void deleteClient(Map<String, String> headers, Long id) {
        httpController.sendRequest(BASE_URL + "/" + id, HttpController.HttpMethod.DELETE, headers, null, ContentType.ANY);
    }
}
