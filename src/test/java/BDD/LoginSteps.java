package BDD;

import Pages.LandingPage;
import BaseTest.Base;
import io.cucumber.java.en.*;
import static org.testng.Assert.*;
import org.apache.log4j.Logger;

public class LoginSteps extends Base {
    LandingPage landingPage;
    static Logger logger = Logger.getLogger(LoginSteps.class);

    @Given("I am on the Swag Labs login page")
    public void i_am_on_the_login_page() {
        invokeBrowser();
        driver.get("https://www.saucedemo.com/");
        landingPage = new LandingPage(driver);
    }

    @When("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String username, String password) {
        logger.info("Testing login with username: " + username + ", password: " + password);
        landingPage.login(username, password);
    }

    @Then("I should see the expected result for {string}")
    public void i_should_see_the_expected_result_for(String username) {
        if (username.equals("locked_out_user")) {
            String error = landingPage.getErrorMessage();
            assertTrue(error.contains("Sorry, this user has been locked out."),
                "Expected locked out error message, but got: " + error);
        } else {
            boolean isLoggedIn = driver.getCurrentUrl().contains("inventory");
            if (username.equals("problem_user") || username.equals("performance_glitch_user") || username.equals("standard_user") || username.equals("visual_user") || username.equals("error_user")) {
                assertTrue(isLoggedIn, "Expected successful login for user: " + username);
            } else {
                String error = landingPage.getErrorMessage();
                assertFalse(error.isEmpty(), "Expected an error message for user: " + username);
            }
        }
        tearBrowser();
    }
}
