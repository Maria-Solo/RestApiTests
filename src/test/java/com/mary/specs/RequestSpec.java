package com.mary.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {
    public static RequestSpecification requestSpec(){

        return new RequestSpecBuilder()
                .setBaseUri("http://localhost:8080")
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                //.setAccept(JSON)//---> Установка Accept
                .build();
    }
}
