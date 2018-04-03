package com.backend;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;

public class HitEndPointAndPrintTest {

    @DataProvider(name = "PerfectCoffeeAnswers")
    public static Object[][] perfectCoffeeAnswers() {
        return new Object[][]{
                {"posts"},
                {"comments"},
                {"albums"},
                {"photos"},
                {"todos"},
                {"users"},

        };
    }

    @Test(dataProvider = "PerfectCoffeeAnswers")
    public void hitEndPointAndPrint(String resource) {
        when()
                .get("http://jsonplaceholder.typicode.com/" + resource)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .prettyPrint();
    }

}