package com.backend;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Part1Test {

    @DataProvider(name = "actions")
    public static Object[][] actions() {
        return new Object[][]{
                {"posts"},
                {"comments"},
                {"albums"},
                {"photos"},
                {"todos"},
                {"users"},
        };
    }

    @Test(dataProvider = "actions")
    public void hitEndPointAndPrint(String resource) {
        given()
                .baseUri("http://jsonplaceholder.typicode.com")
        .when()
                .log()
                .all()
                .get(resource)
        .then()
                .statusCode(200)
                .log()
                .all();
    }

}