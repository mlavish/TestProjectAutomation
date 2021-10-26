package pagemethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pageobjects.CommonHeaderObjects;
import pageobjects.LoginPageObjects;
import utilities.WaitUtil;

public class LoginPageMethods {
	private WebDriver driver;
	private GenericFunctions genericFunctions;
	private LoginPageObjects loginPageObjects;
	private CommonHeaderObjects commonHeaderObjects;

	public LoginPageMethods(WebDriver driver) {
		this.driver = driver;
		genericFunctions = new GenericFunctions(driver);
		loginPageObjects = PageFactory.initElements(driver, LoginPageObjects.class);
		commonHeaderObjects = PageFactory.initElements(driver, CommonHeaderObjects.class);
	}

	public void login(String email, String password) {
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, loginPageObjects.getEmail());
		loginPageObjects.getEmail().sendKeys(email);
		loginPageObjects.getPassword().sendKeys(password);
		loginPageObjects.getLoginBtn().click();
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, commonHeaderObjects.getUserMenuDropdown());
	}
}
