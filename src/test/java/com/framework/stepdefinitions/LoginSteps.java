package com.framework.stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.*;

public class LoginSteps {

    Response response;

    @Given("I open the login page")
    public void openLoginPage() {
        // Opens the page using Selenide
        open("https://the-internet.herokuapp.com/login");
    }

    @When("I enter username {string} and password {string}")
    public void enterCredentials(String username, String password) {
        // Use Selenide selectors and actions
        $("#username").setValue(username);
        $("#password").setValue(password);
        $("#login").click();
    }

    @Then("I should see the homepage")
    public void verifyHomepage() {
        String title = webdriver().driver().getWebDriver().getTitle();
        assertTrue(title != null && title.contains("Home"));
        closeWebDriver();
    }

    @And("Close browser")
    public void closeBrowser() { 
            closeWebDriver();
        }
}
