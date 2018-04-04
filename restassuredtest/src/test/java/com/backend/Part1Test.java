package com.backend;


import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

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
        Response response =
                given()
                        .baseUri("http://jsonplaceholder.typicode.com")
                        .when()
                        .log()
                        .all()
                        .get(resource);

        response
                .then()
                .log()
                .all();

        assertEquals(response.statusCode(), 200);
    }

}