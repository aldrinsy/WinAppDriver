package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import base.BaseTest;
import pages.MainPage;

public class MainPageTest extends BaseTest {

	private MainPage mainPage;
	private static ExtentTest extentTest;
	
	 @BeforeMethod
	    public void setUpMainPage() {
	        mainPage = new MainPage(driver);
	    }

    @Test (testName = "Testing 1 Test Name")
    public void testing1() {
        extentTest.info("testing1 will now start");
        mainPage.clickFileButton();
        extentTest.info("Clicked the File Button");
        Assert.assertTrue(false);  
        extentTest.pass("Assertion is passed");
    }

    @Test (testName = "Testing 2 Test Name")
    public void testing2() {
        extentTest.info("testing2 will now start");
        mainPage.clickFileButton();
        extentTest.info("Clicked the File Button");
        Assert.assertTrue(true);
        extentTest.pass("Assertion is passed");
    }
}