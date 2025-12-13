package Tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import BaseTest.Base;
import Pages.LandingPage;
import Utils.Utility;
import listeners.TestListener;

@Listeners(TestListener.class)
public class smoke extends Base {
    @BeforeMethod
    public void setUp() {
        invokeBrowser();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void tearDown() {
        tearBrowser();
    }

    @Test
    public void testLoginFunctionality() {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.login("standard_user", "secret_sauce");
        // Example assertion: check if login was successful by checking URL or element
        assertTrue(driver.getCurrentUrl().contains("inventory"), "Login failed or did not redirect to inventory page.");
        Utility.takeSnapshot(driver, "screenshots/login_test.png");
    }
}