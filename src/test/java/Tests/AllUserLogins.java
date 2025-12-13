package Tests;

import org.testng.annotations.*;
import Pages.LandingPage;
import Utils.Utility;
import BaseTest.Base;
import java.util.Properties;
import java.util.Arrays;
import java.util.List;
import static org.testng.Assert.*;
import org.testng.annotations.Listeners;
import listeners.TestListener;

@Listeners(TestListener.class)
public class AllUserLogins extends Base {
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
    public void testAllUserLogins() {
        Properties users = Utility.loadUserProperties();
        List<String> usernames = Arrays.asList(
            users.getProperty("user1"),
            users.getProperty("user2"),
            users.getProperty("user3"),
            users.getProperty("user4"),
            users.getProperty("user5"),
            users.getProperty("user6")
        );
        String password = users.getProperty("password");
        for (String username : usernames) {
          driver.get("https://www.saucedemo.com/");
            LandingPage landingPage = new LandingPage(driver);
            landingPage.login(username, password);
            Utility.takeSnapshot(driver, "screenshots/" + username + "_login.png");
        }
    }
}