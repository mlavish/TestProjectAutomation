package tests;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import utilities.BrowserUtil;
import utilities.Config;
import utilities.ScreenShotUtil;
import utilities.WaitUtil;

public class BaseTest {
	public static WebDriver driver;
	public static BrowserUtil browserUtil;
	public static String browserName;
	public static Long testStartTime;
	public static Map<String, Map<String, String>> allFailScreenshots = new LinkedHashMap<String, Map<String, String>>();
	public static Map<String, String> testMethodScreenshots;
	public SoftAssert softAssert;

	@BeforeSuite(alwaysRun = true)
	@Parameters({ "browser" })
	public void beforeSuite(@Optional("chrome") String browser) {
		ScreenShotUtil.cleanAllScreenShots();
		browserName = browser.trim().length() > 0 ? browser
				: new Config().fetchConfig("suiterun.properties", "browserName");
		testStartTime = System.currentTimeMillis();
		browserUtil = new BrowserUtil();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		softAssert = new SoftAssert();
		testMethodScreenshots = new LinkedHashMap<String, String>();
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() {
		browserUtil.quitBrowser();
	}

	public void launchBrowser() throws IOException {
		driver = browserUtil.startBrowser();
		WaitUtil.sleep(5000);
	}

	public void testMethodWrapUp(SoftAssert softAssert, String firstTestParamIfUsed) {
		String ssMethodName = firstTestParamIfUsed.isEmpty() ? Thread.currentThread().getStackTrace()[2].getMethodName()
				: Thread.currentThread().getStackTrace()[2].getMethodName() + "_" + firstTestParamIfUsed;
		allFailScreenshots.put(ssMethodName, testMethodScreenshots);
		System.out.println(allFailScreenshots);
		softAssert.assertAll();
	}

}
