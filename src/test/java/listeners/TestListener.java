package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import Utils.Utility;
import org.openqa.selenium.WebDriver;

public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("[TEST STARTED] " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("[TEST PASSED] " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("[TEST FAILED] " + result.getName());
        Object testClass = result.getInstance();
        try {
            // Use the public getter for driver
            WebDriver driver = (WebDriver) testClass.getClass().getMethod("getDriver").invoke(testClass);
            if (driver != null) {
                String screenshotPath = "screenshots/" + result.getName() + ".png";
                Utility.takeSnapshot(driver, screenshotPath);
                System.out.println("[SCREENSHOT CAPTURED] " + screenshotPath);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Could not capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("[TEST SKIPPED] " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

    @Override
    public void onStart(ITestContext context) {
        System.out.println("[TEST SUITE STARTED] " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("[TEST SUITE FINISHED] " + context.getName());
    }
}