package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPageObjects {
	private WebDriver driver;

	public DashboardPageObjects(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "(//*[@class='carousel-arrow'])[1]")
	private WebElement topLeftArrow;

	@FindBy(xpath = "(//*[@class='carousel-arrow'])[2]")
	private WebElement topRightArrow;

	/* ************** Getters *************** */

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getTopLeftArrow() {
		return topLeftArrow;
	}

	public WebElement getTopRightArrow() {
		return topRightArrow;
	}

}
