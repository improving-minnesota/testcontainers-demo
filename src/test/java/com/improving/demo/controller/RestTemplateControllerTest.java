package com.improving.demo.controller;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import static io.restassured.RestAssured.given;

@Slf4j
@DirtiesContext
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTemplateControllerTest {
    @LocalServerPort
    private Integer serverPort;

    static GenericContainer<?> httpBin = new GenericContainer<>("kong/httpbin")
            .withLogConsumer(new Slf4jLogConsumer(log))
            .withExposedPorts(80);

    @BeforeAll
    public static void beforeAll() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost:" + serverPort + "/rest";
    }

    @DynamicPropertySource
    static void testingProperties(DynamicPropertyRegistry registry) {
        httpBin.start();
        registry.add("http.rest.testing-url", () -> String.format("http://%s:%d", httpBin.getHost(), httpBin.getFirstMappedPort()));
    }

    @Test
    void testRestEndpoints() {
        given()
                .when()
                .delete("/delete")
                .then()
                .statusCode(200);
        given()
                .when()
                .get("/get")
                .then()
                .statusCode(200);
        given()
                .when()
                .post("/post")
                .then()
                .statusCode(200);
        given()
                .when()
                .put("/put")
                .then()
                .statusCode(200);
    }
}
