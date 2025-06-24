package com.framework.stepdefinitions;

import utilities.ConfigReader;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ApiStepDefinitions {

    private static final Logger log = LoggerFactory.getLogger(ApiStepDefinitions.class);
    private Response response;
    private String token;

    @When("I send a login POST request with username {string} and password {string}")
    public void loginAndGetToken(String username, String password) {
        String body = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password);
        log.info("🔐 Sending login request for user: {}", username);
        response = RestAssured
                .given()
                .contentType("application/json")
                .body(body)
                .post(ConfigReader.get("api.login.endpoint"));
        log.info("✅ Login response: {}", response.asPrettyString());
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

    @Then("the status code should be {int}")
    public void validateStatusCode(int expectedStatus) {
        assertEquals(expectedStatus, response.getStatusCode());
    }

    @Then("the response status should be {string}")
    public void validateStatus(String expectedStatus) {
        String actual = response.jsonPath().getString("status");
        assertEquals(expectedStatus, actual);
    }

    @Then("the response should match login schema")
    public void validateLoginSchema() {
        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/login-schema.json"));
    }

    @Then("the response should match profile schema")
    public void validateProfileSchema() {
        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/profile-schema.json"));
    }

    @Then("the user {string} should exist in the database")
    public void userShouldExist(String username) throws Exception {
        DBUtils.connect();
        boolean exists = DBUtils.userExists(username);
        DBUtils.disconnect();
        assertTrue(exists, "User '" + username + "' was not found in the database.");
    }


}
