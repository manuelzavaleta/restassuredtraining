package com.backend;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;


public class Part2 {

    @DataProvider(name = "actionsAndSchemas")
    public static Object[][] actionsAndSchemas() {
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
        given()
        .when()
                .log()
                .all()
                .get("http://jsonplaceholder.typicode.com/" + resources)
        .then()
                .body(matchesJsonSchema(Paths.get("src/test/resources/" + schema).toUri()));
    }

    @DataProvider(name = "postsUrlParams")
    public static Object[][] postsUrlParams() {
        return new Object[][]{
                {"20", 20, 2, "doloribus ad provident suscipit at", "qui consequuntur ducimus possimus quisquam amet similique\nsuscipit porro ipsam amet\neos veritatis officiis exercitationem vel fugit aut necessitatibus totam\nomnis rerum consequatur expedita quidem cumque explicabo"},
                {"50", 50, 5, "repellendus qui recusandae incidunt voluptates tenetur qui omnis exercitationem", "error suscipit maxime adipisci consequuntur recusandae\nvoluptas eligendi et est et voluptates\nquia distinctio ab amet quaerat molestiae et vitae\nadipisci impedit sequi nesciunt quis consectetur"},
                {"100", 100, 10, "at nam consequatur ea labore ea harum", "cupiditate quo est a modi nesciunt soluta\nipsa voluptas error itaque dicta in\nautem qui minus magnam et distinctio eum\naccusamus ratione error aut"},
        };
    }

    @Test(dataProvider = "postsUrlParams")
    public void validatePosts(String urlParam, int expectedId, int expectedUserId, String expectedTitle, String expectedBody) {
        given()
                .pathParam("id", urlParam)
        .when()
                .log()
                .all()
                .get("http://jsonplaceholder.typicode.com/posts/{id}")
        .then()
                .body("id", is(expectedId))
                .body("userId", is(expectedUserId))
                .body("title", is(expectedTitle))
                .body("body", is(expectedBody))
                .log()
                .all();
    }

    @DataProvider(name = "actions")
    public static Object[][] actions() {
        return new Object[][]{
                {"posts", "userId", "1"},
                {"comments", "postId", "1"},
        };
    }

    @Test(dataProvider = "actions")
    public void implementMethods(String resource, String param, String value) {
        given()
                .queryParam(param, value)
        .when()
                .log()
                .all()
                .get("http://jsonplaceholder.typicode.com/" + resource)
        .then()
                .log()
                .all();
    }

    @Test()
    public void extraTest() {
        String findAllById = "findAll {post -> post.id in [20,50,100]}";
        given()
        .when()
                .log()
                .all()
                .get("http://jsonplaceholder.typicode.com/posts")
        .then()
                .body(findAllById + ".id", hasItems(50, 100, 20))
                .body(findAllById + ".userId", hasItems(2, 5, 10))
                .body(findAllById + ".title", hasItems(
                        "at nam consequatur ea labore ea harum",
                        "repellendus qui recusandae incidunt voluptates tenetur qui omnis exercitationem",
                        "doloribus ad provident suscipit at"
                ))
                .body(findAllById + ".body", hasItems(
                        "cupiditate quo est a modi nesciunt soluta\nipsa voluptas error itaque dicta in\nautem qui minus magnam et distinctio eum\naccusamus ratione error aut",
                        "error suscipit maxime adipisci consequuntur recusandae\nvoluptas eligendi et est et voluptates\nquia distinctio ab amet quaerat molestiae et vitae\nadipisci impedit sequi nesciunt quis consectetur",
                        "qui consequuntur ducimus possimus quisquam amet similique\nsuscipit porro ipsam amet\neos veritatis officiis exercitationem vel fugit aut necessitatibus totam\nomnis rerum consequatur expedita quidem cumque explicabo"
                ));
    }

}
