package Tests;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Pages.LandingPage;
import listeners.TestListener;

@Listeners(TestListener.class)

public class ProductNameTest extends BaseTest.Base {
	
	 @BeforeMethod
	    public void setUp() {
	        invokeBrowser();
	        driver.get("https://www.saucedemo.com/");
	       // driver.get("file:///C:/Users/HP/OneDrive/Desktop/New%20folder/HybridFramework/target/Swag%20Labs.html");
	    }

	    @AfterMethod
	    public void tearDown() {
	        tearBrowser();
	    }
	    @Test(priority=1)
	    public void checkItemNames() {
	    	
	    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    	 LandingPage landingPage = new LandingPage(driver);
	         landingPage.login("standard_user", "secret_sauce");
	         Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");
	         List<WebElement> itemNames=driver.findElements(By.xpath("//div[@class='inventory_item_name ']"));
	         
	         List<String> textlist=itemNames.stream().map(s->s.getText()).collect(Collectors.toList());
	         
	         textlist.stream().filter(s->s.contains("Sauce")).forEach(s->System.out.println(s));
	    	
	    }
	    @Test(dependsOnMethods = {"checkItemNames"})
	    public void checkPrice() {
	    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    	 LandingPage landingPage = new LandingPage(driver);
	         landingPage.login("standard_user", "secret_sauce");
	         Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");
	         List<WebElement> itemNames=driver.findElements(By.className("inventory_item_price"));
	         
	    	List<String> prices=itemNames.stream()
	    			.map(s->s.getText())
	    			.collect(Collectors.toList());
	    	
	    	prices.stream().filter(s->s.endsWith("9")).forEach(s->System.out.println(s));
	    	
	    }
	    


}
