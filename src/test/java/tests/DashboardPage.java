package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pagemethods.CommonHeaderMethods;
import pagemethods.DashboardPageMethods;
import pagemethods.GenericFunctions;
import pagemethods.LoginPageMethods;
import utilities.Config;
import utilities.ScreenShotUtil;
import utilities.WaitUtil;

public class DashboardPage extends BaseTest {
	DashboardPageMethods dashboardPageMethods;
	CommonHeaderMethods commonHeaderMethods;
	LoginPageMethods loginPageMethods;
	GenericFunctions genericFunctions;
	Config config;
	public String appUrl;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		if (driver == null) {
			launchBrowser();
		}
		genericFunctions = new GenericFunctions(driver);
		dashboardPageMethods = new DashboardPageMethods(driver);
		commonHeaderMethods = new CommonHeaderMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		config = new Config();
		appUrl = config.fetchConfig("suiterun.properties", "appUrl");
	}

	@Test(enabled = true, description = "This test verifies click on top left arrow on dashboard page.", priority = 2)
	public void verifyTopLeftArrowClick() {
		driver.get(appUrl);
		try {
			WaitUtil.sleep(5000);
			if (!commonHeaderMethods.isUserLoggedIn()) {
				String email = config.fetchConfig("suiterun.properties", "loginUserEmail");
				String password = config.fetchConfig("suiterun.properties", "loginUserPassword");
				loginPageMethods.login(email, password);
				Assert.assertTrue(commonHeaderMethods.isUserLoggedIn(), "Test case terminated due to login failure.");
			}
			WaitUtil.sleep(5000);
			boolean result = dashboardPageMethods.clickTopLeftArrow();
			WaitUtil.sleep(5000);
			softAssert.assertTrue(result,
					"Click on top left arrow on dashboard unsuccessful. Refer attached screenshot.");
			if (!result)
				ScreenShotUtil.takeScreenshot(driver, "leftArrowClickFailure");
		} catch (Exception e) {
			softAssert.assertTrue(false, "Exception occurred : " + e.getMessage());
			ScreenShotUtil.takeScreenshot(driver, "leftArrowClickFailure");
			e.printStackTrace();
		}
		testMethodWrapUp(softAssert, "");
	}

	@Test(enabled = true, description = "This test verifies click on top right arrow on dashboard page.", priority = 3)
	public void verifyTopRightArrowClick() {
		driver.get(appUrl);
		try {
			WaitUtil.sleep(5000);
			if (!commonHeaderMethods.isUserLoggedIn()) {
				String email = config.fetchConfig("suiterun.properties", "loginUserEmail");
				String password = config.fetchConfig("suiterun.properties", "loginUserPassword");
				loginPageMethods.login(email, password);
				Assert.assertTrue(commonHeaderMethods.isUserLoggedIn(), "Test case terminated due to login failure.");
			}
			WaitUtil.sleep(5000);
			boolean result = dashboardPageMethods.clickTopRightArrow();
			WaitUtil.sleep(5000);
			softAssert.assertTrue(result,
					"Click on top right arrow on dashboard unsuccessful. Refer attached screenshot.");
			if (!result)
				ScreenShotUtil.takeScreenshot(driver, "rightArrowClickFailure");
		} catch (Exception e) {
			softAssert.assertTrue(false, "Exception occurred : " + e.getMessage());
			ScreenShotUtil.takeScreenshot(driver, "rightArrowClickFailure");
			e.printStackTrace();
		}
		testMethodWrapUp(softAssert, "");
	}

}
