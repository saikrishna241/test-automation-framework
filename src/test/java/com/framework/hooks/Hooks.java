package com.framework.hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.framework.TestResults.ReportSetup;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import org.openqa.selenium.OutputType;
import utilities.ConfigReader;
import com.codeborne.selenide.Configuration;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    @AfterStep
    public void takeScreenshotIfFailed(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = Selenide.screenshot(OutputType.BYTES);
                String fileName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_") + "_screenshot.png";
                Path dest = Paths.get(ReportSetup.reportFolderPath + "/" + fileName);
                Files.write(dest, screenshot);
                System.out.println("📸 Screenshot saved: " + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
