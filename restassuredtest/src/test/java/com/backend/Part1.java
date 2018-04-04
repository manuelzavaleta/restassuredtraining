package com.backend;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Part1 {

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
        .when()
                .log()
                .all()
                .get("http://jsonplaceholder.typicode.com/" + resource)
        .then()
                .statusCode(200)
                .log()
                .all();
    }

}