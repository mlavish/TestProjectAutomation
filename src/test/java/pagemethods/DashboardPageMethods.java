package pagemethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pageobjects.DashboardPageObjects;
import utilities.WaitUtil;

public class DashboardPageMethods {
	private WebDriver driver;
	private DashboardPageObjects dashboardPageObjects;
	private GenericFunctions genericFunctions;

	public DashboardPageMethods(WebDriver driver) {
		this.driver = driver;
		genericFunctions = new GenericFunctions(driver);
		dashboardPageObjects = PageFactory.initElements(driver, DashboardPageObjects.class);
	}

	public boolean clickTopLeftArrow() {
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, dashboardPageObjects.getTopLeftArrow());
		if (genericFunctions.isElementVisible(dashboardPageObjects.getTopLeftArrow())) {
			dashboardPageObjects.getTopLeftArrow().click();
			WaitUtil.sleep(3000);
			return true;
		} else {
			return false;
		}
	}

	public boolean clickTopRightArrow() {
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, dashboardPageObjects.getTopRightArrow());
		if (genericFunctions.isElementVisible(dashboardPageObjects.getTopRightArrow())) {
			dashboardPageObjects.getTopRightArrow().click();
			WaitUtil.sleep(3000);
			return true;
		} else {
			return false;
		}
	}

}
