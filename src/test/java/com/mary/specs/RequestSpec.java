package com.mary.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RequestSpec {
    public static RequestSpecification requestSpec(){

        return new RequestSpecBuilder()
                .setBaseUri("http://localhost:8080")
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .build();
    }
    public static RequestSpecification requestSpec(Map<String, String > headers){

        return new RequestSpecBuilder()
                .setBaseUri("http://localhost:8080/api")
//                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .addHeaders(headers)
                .build();
    }
}
