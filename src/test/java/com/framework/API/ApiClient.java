package com.framework.API;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiClient {
    private String baseUrl;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Response get(String endpoint) {
        return RestAssured.given()
                .baseUri(baseUrl)
                .when()
                .get(endpoint);
    }

    public Response post(String endpoint, String body) {
        return RestAssured.given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .body(body)
                .when()
                .post(endpoint);
    }

    public Response put(String endpoint, String body) {
        return RestAssured.given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .body(body)
                .when()
                .put(endpoint);
    }

    public Response delete(String endpoint) {
        return RestAssured.given()
                .baseUri(baseUrl)
                .when()
                .delete(endpoint);
    }
}
