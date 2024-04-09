package com.improving.demo.controller;

import com.improving.demo.persistent.Widget;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DirtiesContext
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JdbcControllerTest {
    @LocalServerPort
    private Integer serverPort;

    @BeforeAll
    public static void beforeAll() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost:" + serverPort;
    }

    @Test
    void testWidgetCRUD() {
        // find empty list of Widget's
        var list = given()
                .when()
                .get("/widgets")
                .then()
                .statusCode(200)
                .extract()
                .as(List.class);
        assertThat(list).isEmpty();

        // create Widget
        var created = given()
                .when()
                .contentType(ContentType.JSON)
                .body("{\"value\":\"test widget string\"}")
                .post("/widget")
                .then()
                .statusCode(200)
                .extract()
                .as(Widget.class);
        var id = created.getId();
        assertThat(id).isGreaterThan(0);

        // find Widget
        var found = given()
                .when()
                .get("/widget/{id}", id)
                .then()
                .statusCode(200)
                .extract()
                .as(Widget.class);
        assertThat(found.getId()).isEqualTo(id);
        assertThat(found.getValue()).isEqualTo("test widget string");

        // update Widget
        var updated = given()
                .when()
                .contentType(ContentType.JSON)
                .body("{\"id\":\"" + id + "\",\"value\":\"updated widget string\"}")
                .put("/widget")
                .then()
                .statusCode(200)
                .extract()
                .as(Widget.class);
        assertThat(updated.getId()).isEqualTo(id);
        assertThat(updated.getValue()).isEqualTo("updated widget string");

        // find updated Widget
        found = given()
                .when()
                .get("/widget/{id}", id)
                .then()
                .statusCode(200)
                .extract()
                .as(Widget.class);
        assertThat(found.getId()).isEqualTo(id);
        assertThat(found.getValue()).isEqualTo("updated widget string");

        // delete Widget
        given()
                .when()
                .delete("/widget/{id}", id)
                .then()
                .statusCode(204);

        // find empty list of Widget's
        list = given()
                .when()
                .get("/widgets")
                .then()
                .statusCode(200)
                .extract()
                .as(List.class);
        assertThat(list).isEmpty();
    }
}
