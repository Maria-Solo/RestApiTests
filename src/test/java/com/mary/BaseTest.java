package com.mary;

import com.mary.specs.RequestSpec;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.config.RestAssuredConfig.config;

public class BaseTest {

    @BeforeAll
    public static void setup() {
        RestAssured.requestSpecification = RequestSpec.requestSpec();

        /// тут вызываем мето давторизации
    }
}
