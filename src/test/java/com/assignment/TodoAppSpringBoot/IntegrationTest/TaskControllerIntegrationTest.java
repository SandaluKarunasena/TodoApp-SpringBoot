package com.assignment.TodoAppSpringBoot.controllers;

import com.assignment.TodoAppSpringBoot.models.Task;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import javassist.expr.Instanceof;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testGetAllTasks() {
        given()
                .when()
                .get("/api/v1/tasks/")
                .then()
                .statusCode(200)
                .body("size()", is(greaterThanOrEqualTo(0)))
                .body("id", everyItem(instanceOf(Integer.class)))
                .body("task[0]", equalTo("Write Unit tests with JUnit"))
                .body("id", notNullValue())
                .body("completed", everyItem(instanceOf(boolean.class)));
    }


}
