package com.mary.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mary.HttpController;
import com.mary.models.Client;
import com.mary.models.Provider;
import groovyjarjarantlr4.runtime.tree.RewriteEmptyStreamException;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.platform.engine.support.discovery.SelectorResolver;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ClientApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/clients";
    ObjectMapper mapper = new ObjectMapper();

    HttpController httpController = new HttpController();
    private Map<String, String> headers;


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

    public Client getClientByIdHttp(Long id, Map<String, String> headers){
        return httpController.sendRequest(BASE_URL + "/" + id, HttpController.HttpMethod.GET, headers, null, ContentType.ANY)
                .extract().as(Client.class);

    }

    public List<Client> getAllClients1(Map<String, String> headers){
       var response = httpController.sendRequest(BASE_URL, HttpController.HttpMethod.GET, headers, null, ContentType.ANY)
               .extract().response();
       mapper.registerModule(new JavaTimeModule());
       try {
          return mapper.readValue(response.asString(), new TypeReference<>() {
          }) ;
       }
       catch (JsonProcessingException e) {
           throw new RuntimeException("Failed to deserialize clients", e);
       }
    }
//TODO rewrite these methods using http controller + base url


}
