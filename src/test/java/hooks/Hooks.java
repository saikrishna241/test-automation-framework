package hooks;

import com.codeborne.selenide.Configuration;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import utilities.ConfigReader;
import com.codeborne.selenide.Configuration;


import static com.codeborne.selenide.Selenide.closeWebDriver;

public class Hooks {

    @Before(order = 0)
    public void setupBrowser() {
        // Selenide configuration
        Configuration.browser = ConfigReader.get("browser"); // chrome, firefox, etc.
        Configuration.timeout = ConfigReader.getInt("implicit.wait") * 1000;
        Configuration.browserSize = "1920x1080"; // Sets the browser window size
        Configuration.pageLoadTimeout = ConfigReader.getInt("page.load.timeout") * 1000;
        Configuration.headless = false; // Optional: make true if you want headless
    }

    @Before(order = 1)
    public void setupAPI() {
        RestAssured.baseURI = ConfigReader.get("api.base.url");
    }


    @After
    public void tearDown() {
        closeWebDriver(); // Selenide will close browser
    }
}
