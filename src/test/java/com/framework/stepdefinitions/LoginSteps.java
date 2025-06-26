package com.framework.stepdefinitions;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.*;
import utilities.ConfigReader;
import utilities.ElementFinder;
import utilities.PageContextManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.*;

public class LoginSteps {
    private static final Logger log = LoggerFactory.getLogger(LoginSteps.class);

    @Given("I open the website page")
    public void openLoginPage() {
        // Opens the page using Selenide
        open(ConfigReader.get("ui.base.url"));
        log.info("Website launched sucessfully");
    }

    @Then("verify that {string} page is displayed")
    public void verifyPageDisplayed(String pageName) throws Exception {
        // Your logic to verify any element exists on page (optional)
        // Set page context for next steps
        PageContextManager.setCurrentPage(pageName);
    }

    @Then("I should see the homepage title as {string}")
    public void verifyHomepage(String expTitle) {
        String title = webdriver().driver().getWebDriver().getTitle();
        assertTrue(title != null && title.contains(expTitle));
        log.info("Page title verified successfully");
    }

    @And("Capture screenshot")
    public void captureScreenshot() {
        Selenide.screenshot("Screenshot");
    }

    @When("user enters text {string} in {string} control")
    public void enterText(String input, String logicalName) throws Exception {
        String pageName = PageContextManager.getCurrentPage();
        ElementFinder.getElement(pageName, logicalName).setValue(input);
        log.info("Entered text",input,"in",logicalName,"control");
    }

    @Then("scroll to {string} control")
    public void scrollToControl(String logicalName) throws Exception {
        String pageName = PageContextManager.getCurrentPage();
        ElementFinder.getElement(pageName, logicalName).scrollIntoView(true);
        log.info("Scrolled to ",logicalName,"control");
    }

    @Then("user clicks on {string} control")
    public void clickControl(String logicalName) throws Exception {
        String pageName = PageContextManager.getCurrentPage();
        ElementFinder.getElement(pageName, logicalName).click();
    }

    @Then("select by {string} as {string} in {string} control")
    public void selectDropdown(String selectorType, String value, String logicalName) throws Exception {
        String pageName = PageContextManager.getCurrentPage();
        SelenideElement dropdown = ElementFinder.getElement(pageName, logicalName);
        switch (selectorType.toLowerCase()) {
            case "text":
                dropdown.selectOption(value);
                break;
            case "value":
                dropdown.selectOptionByValue(value);
                break;
            case "index":
                dropdown.selectOption(Integer.parseInt(value));
                break;
        }
    }

    @Then("clear text on {string} control")
    public void clearText(String logicalName) {
        String pageName = PageContextManager.getCurrentPage();
        ElementFinder.getElement(pageName, logicalName).clear();
    }

    @And("verify that control {string} contains {string} text")
    public void verifytext(String logicalName, String expText){
        String pageName = PageContextManager.getCurrentPage();
        String UIvalue = ElementFinder.getElement(pageName, logicalName).getText();
        log.info("UI value found: {}", UIvalue);
        ElementFinder.getElement(pageName, logicalName).shouldHave(text(expText));
    }

    @And("Close browser")
    public void closeBrowser() {
        closeWebDriver();
    }
}
