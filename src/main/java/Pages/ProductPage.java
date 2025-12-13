package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {
	
	WebDriver driver;
	
	By pagelogo= By.xpath("//*[@class='app_logo']");
	
	
	
	public ProductPage(WebDriver driver){
		this.driver = driver;
		
	}

	// Validate the page logo is displayed
	
	public boolean validatePageLogo() {
		
	return "Swag Labs".equals(driver.findElement(pagelogo).getText());
	}
	
	// Check all links on the product page for broken links using Utility
    public void checkBrokenLinks() {
        Utils.Utility.checkAllLinks(driver);
    }
	
	}