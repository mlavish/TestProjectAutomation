package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageObjects {
	private WebDriver driver;

	public LoginPageObjects(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(id = "username")
	private WebElement email;

	@FindBy(id = "password")
	private WebElement password;

	@FindBy(id = "tp-sign-in")
	private WebElement loginBtn;

	@FindBy(id = "tp-signup-link")
	private WebElement signupLink;

	/* ************** Getters *************** */

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getEmail() {
		return email;
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getLoginBtn() {
		return loginBtn;
	}

	public WebElement getSignupLink() {
		return signupLink;
	}

}
