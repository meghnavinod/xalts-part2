package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.xalts",
    plugin = {"pretty", "html:target/cucumber-report.html"},
    monochrome = true
)
public class testRunner extends AbstractTestNGCucumberTests {}