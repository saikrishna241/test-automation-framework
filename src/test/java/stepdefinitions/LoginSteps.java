package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.testng.Assert.*;

public class LoginSteps {

    WebDriver driver;
    Response response;

    @Given("I open the login page")
    public void openLoginPage() {
        driver = new ChromeDriver();
        driver.get("https://example.com/login");
    }

    @When("I enter username {string} and password {string}")
    public void enterCredentials(String username, String password) {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login")).click();
    }

    @Then("I should see the homepage")
    public void verifyHomepage() {
        assertTrue(driver.getTitle().contains("Home"));
        driver.quit();
    }

    @When("I send a login POST request with username {string} and password {string}")
    public void sendLoginApi(String username, String password) {
        response = RestAssured
                .given()
                .contentType("application/json")
                .body("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }")
                .post("https://api.example.com/login");
    }

    @Then("the response status should be {int}")
    public void verifyResponse(int statusCode) {
        assertEquals(response.getStatusCode(), statusCode);
    }
}
