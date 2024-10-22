package base;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.appium.java_client.windows.WindowsDriver;

public class BaseTest {

	protected static WindowsDriver driver;
	public static ExtentReports extentReports;
	public static ExtentTest extentTest;
	public static String screenshotSubFolderName;
	private Process winAppDriverProcess;

	@BeforeSuite
	public void initializeExtentReport() {
		ExtentSparkReporter sparkReporter_all = new ExtentSparkReporter("ExtentReport.html");
		extentReports = new ExtentReports();
		extentReports.attachReporter(sparkReporter_all);
		extentReports.setSystemInfo("OS", System.getProperty("os.name"));
		startWinAppDriver();

	}

	@AfterSuite
	public void generateExtentReport() throws Exception {
		extentReports.flush();
		Desktop.getDesktop().browse(new File("ExtentReport.html").toURI());
		stopWinAppDriver();
	}

	@BeforeMethod
	public void setUp(ITestContext context, Method method) {
		Test testAnnotation = method.getAnnotation(Test.class);
	    String testName = testAnnotation.testName();
		extentTest = extentReports.createTest(testName);
		setupDriver();
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		handleTestResult(result);
		if (driver != null) {
			driver.quit();
		}
	}
	
	private void setupDriver() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("app", "C:\\Windows\\System32\\notepad.exe"); // Path to Notepad.exe
		capabilities.setCapability("platformName", "Windows");
		capabilities.setCapability("deviceName", "WindowsPC");
		try {
			driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities); // Correctly passing URL
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private void handleTestResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                String screenshotPath = takeScreenshot(result.getName());
                extentTest.fail("Test Failed. Screenshot attached.")
                           .addScreenCaptureFromPath(screenshotPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.pass("Test passed successfully.");
        }
    }

	public String takeScreenshot(String testName) throws IOException {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String screenshotPath = "screenshots/" + testName + ".png";
		File destFile = new File(screenshotPath);
		FileHandler.copy(srcFile, destFile);
		return screenshotPath;
	}
	
	private void startWinAppDriver() {
	    try {
	        // Define the full path to the WinAppDriver.exe file
	        String winAppDriverPath = new File("src/test/resources/Windows Application Driver/WinAppDriver.exe").getAbsolutePath();
	        
	        // Start the WinAppDriver.exe process using ProcessBuilder
	        ProcessBuilder processBuilder = new ProcessBuilder(winAppDriverPath);
	        
	        // Redirect stdout and stderr to a log file
	        File logFile = new File("WinAppDriver.log");
	        processBuilder.redirectOutput(logFile);
	        processBuilder.redirectError(logFile);
	        
	        // Start the process
	        winAppDriverProcess = processBuilder.start();
	        System.out.println("WinAppDriver started.");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

    private void stopWinAppDriver() {
        if (winAppDriverProcess != null) {
            winAppDriverProcess.destroy();
            System.out.println("WinAppDriver stopped.");
        }
    }

}
