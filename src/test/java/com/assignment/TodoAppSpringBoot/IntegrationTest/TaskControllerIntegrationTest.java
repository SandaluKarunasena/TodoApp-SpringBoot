package com.assignment.TodoAppSpringBoot.IntegrationTest;

import com.assignment.TodoAppSpringBoot.models.Task;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import javassist.expr.Instanceof;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

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
                .body("task[0]", equalTo("Refactor the code according to the existing architecture"))
                .body("id", notNullValue())
                .body("completed", everyItem(instanceOf(boolean.class)));
    }

    @Test
    public void testGetTaskById() {
        int targetId = 3;

        // Perform the HTTP request
        given()
                .pathParam("id", targetId)
                .when()
                .get("/api/v1/tasks/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(targetId))
                .body("task", equalTo("New Integration Testing Task"));
    }

    @Test
    public void testCreateTask() {
        Task newTask = new Task(3L, "New Integration Testing Task", false);
        given()
                .contentType(ContentType.JSON)
                .body(newTask)
                .when()
                .post("/api/v1/tasks/")
                .then()
                .statusCode(200)
                .body("task", equalTo(newTask.getTask()))
                .body("id", notNullValue())
                .body("task", notNullValue())
                .body("completed", notNullValue())
                .body("completed", equalTo(newTask.isCompleted())); // Add more assertions based on your data
    }

    @Test
    public void testUpdateTask() {
        Long taskId = 1L; // Replace with a valid task ID
        Task updatedTask = new Task(taskId, "Updated Integration Testing Task", true);
        given()
                .contentType(ContentType.JSON)
                .body(updatedTask)
                .when()
                .put("/api/v1/tasks/" + taskId)
                .then()
                .statusCode(200)
                .body("task", equalTo(updatedTask.getTask()))
                .body("completed", is(updatedTask.isCompleted())); // Add more assertions based on your data
    }

    @Test
    public void testDeleteTask() {
        Long existingTaskId = 1L; //Existing Id to pass the test

        // Perform the HTTP DELETE request
        given()
                .pathParam("id", existingTaskId)
                .when()
                .delete("/api/v1/tasks/{id}")
                .then()
                .statusCode(200)
                .body(equalTo("Delete Successful"));

        // Verify that the task with the specified ID is no longer accessible
        given()
                .pathParam("id", existingTaskId)
                .when()
                .get("/api/v1/tasks/{id}")
                .then()
                .statusCode(200)
                .body(not(hasValue(nullValue())));// Assuming you return 200 when the task is not found

        // Verify that the list of all tasks no longer contains the deleted task
        given()
                .when()
                .get("/api/v1/tasks")
                .then()
                .statusCode(404)
                .body("id", not(hasItem(existingTaskId.intValue())));
    }
}
