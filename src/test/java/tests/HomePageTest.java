package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.MainPage;

public class HomePageTest extends BaseTest {

	private MainPage mainPage;
	
	 @BeforeMethod
	    public void setUpMainPage() {
			System.out.println("setUp HomePage");
	        mainPage = new MainPage(driver);
	    }

    @Test (testName = "Testing 3 Test Name" )
    public void testing3() {
        extentTest.info("testing3 will now start");
        mainPage.clickFileButton();
        extentTest.info("Clicked the File Button");
        Assert.assertTrue(false);  
        extentTest.pass("Assertion is passed");
    }

    @Test (testName = "Testing 4 Test Name" )
    public void testing4() {
        extentTest.info("testing4 will now start");
        mainPage.clickFileButton();
        extentTest.info("Clicked the File Button");
        Assert.assertTrue(true);
        extentTest.pass("Assertion is passed");
    }
}