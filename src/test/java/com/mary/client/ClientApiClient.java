package com.mary.client;

import groovyjarjarantlr4.runtime.tree.RewriteEmptyStreamException;
import io.restassured.response.Response;
import org.junit.platform.engine.support.discovery.SelectorResolver;

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
                .get("/clients/" + id);
    }

}
