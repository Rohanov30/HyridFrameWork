package Utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Properties;
import java.io.InputStream;

public class Utility {
	public static void takeSnapshot(WebDriver driver, String filePath) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(filePath);
        try {
            Files.createDirectories(destFile.getParentFile().toPath());
            Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot: " + filePath, e);
        }
    }

    public static Properties loadUserProperties() {
        Properties props = new Properties();
        try (InputStream input = Utility.class.getClassLoader().getResourceAsStream("users.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find users.properties");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load users.properties", e);
        }
        return props;
    }

    public static void checkAllLinks(WebDriver driver) {
        List<WebElement> links = driver.findElements(By.tagName("a"));
        for (WebElement link : links) {
            String url = link.getAttribute("href");
            if (url == null || url.isEmpty()) {
                System.out.println("[SKIPPED] Empty or null href for link: " + link.getText());
                continue;
            }
            try {
                HttpURLConnection connection = (HttpURLConnection) (new URL(url).openConnection());
                connection.setRequestMethod("HEAD");
                connection.setConnectTimeout(3000);
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode >= 400) {
                    System.out.println("[BROKEN LINK] " + url + " -- Response Code: " + responseCode);
                } else {
                    System.out.println("[VALID LINK] " + url + " -- Response Code: " + responseCode);
                }
            } catch (Exception e) {
                System.out.println("[ERROR] Could not check link: " + url + " -- " + e.getMessage());
            }
        }
    }
}