package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CommonHeaderObjects {
	private WebDriver driver;

	public CommonHeaderObjects(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(id = "user-popup-panel-menuitem")
	private WebElement userMenuDropdown;

	@FindBy(xpath = "//div[@id='user-popup-panel-menuitem']//a[@id='my-profile-link']")
	private WebElement profileLink;
	
	@FindBy(xpath = "//div[@id='user-popup-panel-menuitem']//a[text()='Logout']")
	private WebElement logoutLink;

	/* ************** Getters *************** */

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getUserMenuDropdown() {
		return userMenuDropdown;
	}

	public WebElement getProfileLink() {
		return profileLink;
	}

	public WebElement getLogoutLink() {
		return logoutLink;
	}
}
