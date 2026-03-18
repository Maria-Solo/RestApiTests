package com.mary.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.http.ContentType.JSON;

public class ResponseSpec {
    public static ResponseSpecification responseSpecificationScOk() {
        return new ResponseSpecBuilder()
                .log(LogDetail.STATUS)//---> Уровень логирования
                .expectContentType(JSON)//---> Ожидаемый Content Type
                //.expectStatusCode(HttpStatus.SC_OK)//---> Ожидаемый Status Code
                //.expectResponseTime(lessThanOrEqualTo(3L), SECONDS)//---> Ожидаемое время ответа максимум 3 секунды
                .build();
    }
}
