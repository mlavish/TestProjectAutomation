package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignupPageObjects {
	private WebDriver driver;

	public SignupPageObjects(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(id = "email")
	private WebElement enterEmail;

	@FindBy(id = "tp-continue-btn")
	private WebElement continueBtn;

	@FindBy(id = "tp-sign-in")
	private WebElement loginBtn;

	@FindBy(id = "first-name")
	private WebElement firstName;

	@FindBy(id = "last-name")
	private WebElement lastName;

	@FindBy(id = "password")
	private WebElement password;

	@FindBy(id = "eula")
	private WebElement eulaCheckbox;

	@FindBy(id = "tp-sign-up")
	private WebElement signupBtn;

	@FindBy(className = "tp-message")
	private WebElement signupSuccessMsg;

	/* ************** Getters *************** */

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getEnterEmail() {
		return enterEmail;
	}

	public WebElement getContinueBtn() {
		return continueBtn;
	}

	public WebElement getLoginBtn() {
		return loginBtn;
	}

	public WebElement getFirstName() {
		return firstName;
	}

	public WebElement getLastName() {
		return lastName;
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getEulaCheckbox() {
		return eulaCheckbox;
	}

	public WebElement getSignupBtn() {
		return signupBtn;
	}

	public WebElement getSignupSuccessMsg() {
		return signupSuccessMsg;
	}

}
