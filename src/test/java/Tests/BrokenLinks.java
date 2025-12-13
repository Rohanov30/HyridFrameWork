package Tests;

import org.testng.annotations.*;
import Pages.ProductPage;
import Utils.Utility;
import listeners.TestListener;
import BaseTest.Base;
@Listeners(TestListener.class)
public class BrokenLinks extends Base {
    @BeforeMethod
    public void setUp() {
        invokeBrowser();
        driver.get("https://www.saucedemo.com/");
        // Login to reach product page (assuming standard_user)
        driver.findElement(org.openqa.selenium.By.id("user-name")).sendKeys("standard_user");
        driver.findElement(org.openqa.selenium.By.id("password")).sendKeys("secret_sauce");
        driver.findElement(org.openqa.selenium.By.id("login-button")).click();
    }

    @AfterMethod
    public void tearDown() {
        tearBrowser();
    }

    @Test
    public void verifyBrokenLinksOnProductPage() {
        ProductPage productPage = new ProductPage(driver);
        // Optionally, add navigation or checks to ensure on product page
        Utility.takeSnapshot(driver, "screenshots/product_page.png");
     System.out.print(productPage.validatePageLogo());
        Utility.checkAllLinks(driver);
    }
}