package BDD;

import BaseTest.Base;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Hooks extends Base {
    static Logger logger = Logger.getLogger(Hooks.class);

    @Before
    public void beforeScenario(Scenario scenario) {
        logger.info("========== Starting scenario: " + scenario.getName() + " ==========");
        // Initialize browser and navigate to base URL before each scenario
        invokeBrowser();
        driver.get("https://www.saucedemo.com/");
    }

    @After
    public void afterScenario(Scenario scenario) {
        logger.info("========== Finished scenario: " + scenario.getName() + " - cleaning up ==========");
        try {
            if (scenario.isFailed()) {
                // Capture screenshot as bytes and attach to the Cucumber report
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                try {
                    scenario.attach(screenshot, "image/png", "Failed - " + scenario.getName());
                } catch (Exception e) {
                    logger.warn("Could not attach screenshot to scenario: " + e.getMessage());
                }

                // Also write screenshot to project screenshots/ directory
                String safeName = scenario.getName().replaceAll("[^a-zA-Z0-9\\-_\\.]", "_") + ".png";
                Path dest = Paths.get(System.getProperty("user.dir"), "screenshots", safeName);
                try {
                    Files.createDirectories(dest.getParent());
                    Files.write(dest, screenshot);
                    logger.info("Saved screenshot to: " + dest.toString());
                } catch (IOException e) {
                    logger.error("Failed to write screenshot file: " + e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            logger.error("Error while handling after-scenario actions: " + e.getMessage(), e);
        } finally {
            // Always tear down the browser session after each scenario
            try {
                tearBrowser();
            } catch (Exception e) {
                logger.warn("Error tearing down browser: " + e.getMessage());
            }
        }
    }
}
