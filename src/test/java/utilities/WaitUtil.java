package utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtil {

	public static void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void applyImplicitWait(WebDriver driver, int timeInSeconds) {
		driver.manage().timeouts().implicitlyWait(timeInSeconds, TimeUnit.SECONDS);
	}

	public static WebDriverWait explicitWaitByVisibilityOfElement(WebDriver driver, int seconds,
			WebElement webElement) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.visibilityOf(webElement));
		return wait;
	}

	public static void explicitWaitByInVisibilityOfElement(WebDriver driver, int seconds, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public static void explicitWaitByElementToBeClickable(WebDriver driver, int seconds, WebElement elt) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeClickable(elt));
	}

	/**
	 * To find element untill it's displayed till the max time. If the element is
	 * not found within given time, it will return as {@null}
	 * 
	 * @param driver
	 * @param by
	 * @param maxWaitInSec
	 * @return
	 * @throws InterruptedException
	 */
	public static WebElement poolingWaitForEltWithoutException(WebDriver driver, By by, int maxWaitInSec)
			throws InterruptedException {
		WebElement elt = null;
		for (int i = 0; i < maxWaitInSec; i++) {
			try {
				elt = driver.findElement(by);
				if (elt.isDisplayed()) {
					break;
				}
			} catch (Exception e) {
				WaitUtil.sleep(1000);
			}
		}
		return elt;
	}

	/**
	 * To find element until it's displayed till the max time. If the element is not
	 * found within given time, it will throw Exception
	 * 
	 * @param driver
	 * @param by
	 * @param maxWaitInSec
	 * @return
	 * @throws ElementNotFoundException
	 */
	public static WebElement poolingWaitForElt(WebDriver driver, WebElement element, int maxWaitInSec)
			throws Exception {
		WebElement elt = null;
		boolean eltFound = false;
		for (int i = 0; i < maxWaitInSec; i++) {
			try {
				if (element.isDisplayed()) {
					eltFound = true;
					break;
				}
			} catch (Exception e) {
				WaitUtil.sleep(1000);
			}
		}

		if (eltFound == false)
			throw new ElementNotVisibleException("Element Not Found within " + maxWaitInSec + "secs");
		return elt;
	}

	public static WebElement poolingWaitForElt(WebDriver driver, By locator, int maxWaitInSec) {
		WebElement elt = null;
		boolean eltFound = false;
		for (int i = 0; i < maxWaitInSec; i++) {
			try {
				elt = driver.findElement(locator);
				if (elt.isDisplayed()) {
					eltFound = true;
					break;
				}
			} catch (Exception e) {
				WaitUtil.sleep(1000);
			}
		}
		if (eltFound == false)
			throw new ElementNotVisibleException(
					"WebElement :" + elt + "Element Not Found within" + maxWaitInSec + "secs");
		return elt;
	}

}
