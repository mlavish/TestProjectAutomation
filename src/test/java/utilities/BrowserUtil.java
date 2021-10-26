/**
 * 
 */
package utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import tests.BaseTest;

public class BrowserUtil {

	private final String driverExePath = System.getProperty("user.dir") + "/src/main/resources/drivers/";
	private WebDriver driver;
	private String browser;

	public WebDriver startBrowser() {
		browser = BaseTest.browserName;
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", driverExePath.concat("chromedriver.exe"));
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", driverExePath.concat("geckodriver.exe"));
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(
				Long.parseLong(new Config().fetchConfig("suiterun.properties", "PageLoadTime")), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(
				Long.parseLong(new Config().fetchConfig("suiterun.properties", "ElementWaitTime")), TimeUnit.SECONDS);
		return driver;
	}

	public void quitBrowser() {
		if (driver != null)
			driver.quit();
	}

}