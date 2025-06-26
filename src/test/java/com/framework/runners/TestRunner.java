package com.framework.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeSuite;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.framework.stepdefinitions", "com.framework.hooks"},
        plugin = {
                "pretty"
        },
        monochrome = true,
        tags = "@LaunchGoogleandPerformOperations"
)

public class TestRunner extends AbstractTestNGCucumberTests {

}
