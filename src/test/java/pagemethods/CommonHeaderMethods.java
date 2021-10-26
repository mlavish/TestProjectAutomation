package pagemethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pageobjects.CommonHeaderObjects;
import utilities.WaitUtil;

public class CommonHeaderMethods {
	private WebDriver driver;
	private CommonHeaderObjects commonHeaderObjects;
	private GenericFunctions genericFunctions;

	public CommonHeaderMethods(WebDriver driver) {
		this.driver = driver;
		genericFunctions = new GenericFunctions(driver);
		commonHeaderObjects = PageFactory.initElements(driver, CommonHeaderObjects.class);
	}

	public boolean isUserLoggedIn() {
		try {
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 10, commonHeaderObjects.getUserMenuDropdown());
		} catch (Exception e) {
			e.getMessage();
		}
		return genericFunctions.isElementVisible(commonHeaderObjects.getUserMenuDropdown());
	}

	public void hoverOnUserMenu() {
		if (isUserLoggedIn()) {
			genericFunctions.moveToElement(commonHeaderObjects.getUserMenuDropdown());
		}
	}

	public boolean clickProfileLink() {
		if (genericFunctions.isElementVisible(commonHeaderObjects.getProfileLink())) {
			commonHeaderObjects.getProfileLink().click();
			return true;
		} else {
			return false;
		}
	}

	public boolean logout() {
		if (genericFunctions.isElementVisible(commonHeaderObjects.getLogoutLink())) {
			commonHeaderObjects.getLogoutLink().click();
			WaitUtil.sleep(5000);
			return true;
		} else {
			return false;
		}
	}

}
