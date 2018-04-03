package com.backend;


import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;

public class HitEndPointAndPrintTest {

    @Test
    public void hitEndPointAndPrint() {
        when()
                .get("http://jsonplaceholder.typicode.com/posts")
        .then()
                .statusCode(200)
                .extract()
                .response()
                .prettyPrint();
    }

}