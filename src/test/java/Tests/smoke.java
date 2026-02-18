package Tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import BaseTest.Base;
import Pages.LandingPage;
import Utils.Utility;
import listeners.TestListener;

@Listeners(TestListener.class)
public class smoke extends Base {
    @BeforeMethod
    @Parameters("url")
    public void setUp(String url) {
        invokeBrowser();
        driver.get(url);
    }

    @AfterMethod
    public void tearDown() {
        tearBrowser();
    }

    @Test(dataProvider = "loginData")
    public void testLoginFunctionality(String username, String password) {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.login(username,password);
        // Example assertion: check if login was successful by checking URL or element
        assertTrue(driver.getCurrentUrl().contains("inventory"), "Login failed or did not redirect to inventory page.");
        Utility.takeSnapshot(driver, "screenshots/login_test.png");
    }
    @DataProvider(name = "loginData")
    public Object[][] loginData() {
		return new Object[][] {
			{"standard_user", "secret_sauce"}
		};
    
}
}