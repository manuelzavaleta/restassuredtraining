package com.backend;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;


public class Part2 {

    @DataProvider(name = "actionsAndSchemas")
    public static Object[][] perfectCoffeeAnswers() {
        return new Object[][]{
                {"posts", "schema_posts.json"},
                {"comments", "schema_comments.json"},
                {"albums", "schema_albums.json"},
                {"photos", "schema_photos.json"},
                {"todos", "schema_todos.json"},
                {"users", "schema_users.json"},
        };
    }

    @Test(dataProvider = "actionsAndSchemas")
    public void validateSchema(String resources, String schema) {
        when()
                .get("http://jsonplaceholder.typicode.com/" + resources)
        .then()
                .body(matchesJsonSchema(Paths.get("src/test/resources/" + schema).toUri()))
                .log()
                .all();
    }

}
