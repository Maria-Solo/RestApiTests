package com.mary;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class HttpController {

    public enum HttpMethod {
        GET, POST, PUT, DELETE, PATCH, OPTIONS, HEAD
    }

    public ValidatableResponse sendRequest(String url,
                                           HttpMethod method,
                                           Map<String, String> headers,
                                           Object body,
                                           ContentType contentType) {
         
        RequestSpecification request = prepareRequest(headers, body, contentType);

        return executeRequest(request, url, method);
    }

    private RequestSpecification prepareRequest(Map<String, String> headers, Object body, ContentType contentType) {
        RequestSpecification request = RestAssured.given();

        if (headers != null && !headers.isEmpty()) {
            request.headers(headers);
        }
        if (body != null) {
            request.body(body);
        }

        request.contentType(contentType != null ? contentType : ContentType.JSON);

        return request;
    }

    private ValidatableResponse executeRequest(RequestSpecification request, String url, HttpMethod method) {
        return switch (method) {
            case POST -> request.log().all().post(url).then().log().all();
            case PUT -> request.log().all().put(url).then().log().all();
            case DELETE -> request.log().all().delete(url).then().log().all();
            case PATCH -> request.log().all().patch(url).then().log().all();
            case GET -> request.log().all().get(url).then().log().all();
            case OPTIONS -> request.log().all().options(url).then().log().all();
            case HEAD -> request.log().all().head(url).then().log().all();
        };
    }
}
