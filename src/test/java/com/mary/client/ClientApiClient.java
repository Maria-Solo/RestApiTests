package com.mary.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mary.models.Client;
import com.mary.models.Provider;
import groovyjarjarantlr4.runtime.tree.RewriteEmptyStreamException;
import io.restassured.response.Response;
import org.junit.platform.engine.support.discovery.SelectorResolver;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ClientApiClient {

    public Response getAll(){
        return given()
                .when()
                .get("/clients");
    }

    public Response getById(int id){
        return given()
                .when()
                .get("/clients/" + id);
    }

    public Response create(String body){
        return given()
                .body(body)
                .when()
                .post("/clients");
    }

    public Response update(String body, int id){
        return given()
                .body(body)
                .when()
                .put("/clients/" + id);
    }

    public Response delete(int id) {
        return given()
                .when()
                .delete("/clients/" + id);
    }

    public List<Client> getAllClients(){
        Response response = given()
                .when()
                .get("/clients");
                response.then().statusCode(200);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String jsonString = response.asString();
        try {
            return mapper.readValue(jsonString, new TypeReference<List<Client>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize clients", e);
        }
    }

    public Client getClientById(Long id){
        Response response = given()
                .when()
                .get("/clients/" + id);
        response.then().statusCode(200);
        return response.as(Client.class);
    }

    public Client createClient(Client client){
        Response response = given()
                .body(client)
                .when()
                .post("/clients");
        response.then().statusCode(201);
        return response.as(Client.class);
    }

    public Client updateClient(Long id, Client client){
        Response response = given()
                .body(client)
                .when()
                .put("/clients/" + id);
        response.then().statusCode(200);
        return response.as(Client.class);
    }

    public void deleteClient(Long id){
        Response response = given()
                .when()
                .delete("/clients/" + id);
        response.then().statusCode(204);
    }

}
