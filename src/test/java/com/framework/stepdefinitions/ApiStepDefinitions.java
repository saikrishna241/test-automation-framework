package com.framework.stepdefinitions;

import utilities.ConfigReader;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiStepDefinitions {

    private Response response;
    private String token;

    @When("I send a login POST request with username {string} and password {string}")
    public void loginAndGetToken(String username, String password) {
        String body = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password);

        response = RestAssured
                .given()
                .contentType("application/json")
                .body(body)
                .post(ConfigReader.get("api.login.endpoint"));

        if (response.getStatusCode() == 200) {
            token = response.jsonPath().getString("token");
        }
    }

    @Then("I should receive a JWT token")
    public void verifyJwtToken() {
        assertThat(token).isNotNull().isNotEmpty();
    }

    @Given("I have a valid token")
    public void storeValidToken() {
        // You can reuse login logic here too
        loginAndGetToken("admin", "pass");
        assertThat(token).isNotNull();
    }

    @When("I send a GET request to the profile endpoint")
    public void callProfileApi() {
        response = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .get(ConfigReader.get("api.profile.endpoint"));
    }

    @Then("the response should contain username {string} and role {string}")
    public void verifyProfileResponse(String expectedUser, String expectedRole) {
        assertThat(response.jsonPath().getString("username")).isEqualTo(expectedUser);
        assertThat(response.jsonPath().getString("role")).isEqualTo(expectedRole);
    }
}
