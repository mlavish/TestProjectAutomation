package pagemethods;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import utilities.Config;
import utilities.WaitUtil;

public class GenericFunctions {
	private WebDriver driver;

	public GenericFunctions(WebDriver driver) {
		this.driver = driver;
	}

	public boolean isElementVisible(WebElement element) {
		boolean result;
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		try {
			if (element.isDisplayed()) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			result = false;
		}
		driver.manage().timeouts().implicitlyWait(
				Long.parseLong(new Config().fetchConfig("suiterun.properties", "ElementWaitTime")), TimeUnit.SECONDS);
		return result;
	}

	public void moveToElement(WebElement element) {
		Actions act = new Actions(driver);
		act.moveToElement(element).build().perform();
		WaitUtil.sleep(2000);
	}

	public void clickElementUsingJSE(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

}