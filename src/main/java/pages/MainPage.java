package pages;

import org.openqa.selenium.By;
import io.appium.java_client.windows.WindowsDriver;

public class MainPage {
	

	private WindowsDriver driver;
	
	public MainPage(WindowsDriver driver) {
		this.driver = driver;
	}
	
	private By FileButton = By.name("File");

    public void clickFileButton() {
    	driver.findElement(FileButton).click();
    }
}