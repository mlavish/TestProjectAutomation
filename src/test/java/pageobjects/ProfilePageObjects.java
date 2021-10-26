package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProfilePageObjects {
	private WebDriver driver;

	public ProfilePageObjects(WebDriver driver) {
		this.driver = driver;
	}
	
	@FindBy(xpath = "//div[contains(@class,'common-profile-account')]")
	private WebElement profilePageDiv;

	@FindBy(xpath = "//div[contains(text(),'First Name')]/following-sibling::div/span")
	private WebElement firstName;

	@FindBy(xpath = "//div[contains(text(),'Last Name')]/following-sibling::div/span")
	private WebElement lastName;

	/* ************** Getters *************** */

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getProfilePageDiv() {
		return profilePageDiv;
	}

	public WebElement getFirstName() {
		return firstName;
	}

	public WebElement getLastName() {
		return lastName;
	}
}
