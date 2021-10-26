package tests;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pagemethods.CommonHeaderMethods;
import pagemethods.GenericFunctions;
import pagemethods.LoginPageMethods;
import utilities.Config;
import utilities.ScreenShotUtil;
import utilities.WaitUtil;

public class LoginPage extends BaseTest {
	LoginPageMethods loginPageMethods;
	CommonHeaderMethods commonHeaderMethods;
	GenericFunctions genericFunctions;
	Config config;
	public String appUrl;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		if (driver == null) {
			launchBrowser();
		}
		genericFunctions = new GenericFunctions(driver);
		loginPageMethods = new LoginPageMethods(driver);
		commonHeaderMethods = new CommonHeaderMethods(driver);
		config = new Config();
		appUrl = config.fetchConfig("suiterun.properties", "appUrl");
	}

	@Test(enabled = true, description = "This test verifies login functionality.", priority = 1)
	public void verifyLogin() {
		driver.get(appUrl);
		try {
			WaitUtil.sleep(5000);
			String email = config.fetchConfig("suiterun.properties", "loginUserEmail");
			String password = config.fetchConfig("suiterun.properties", "loginUserPassword");
			loginPageMethods.login(email, password);
			boolean result = commonHeaderMethods.isUserLoggedIn();
			softAssert.assertTrue(result, "Login unsuccessful. Refer attached screenshot.");
			if (!result)
				ScreenShotUtil.takeScreenshot(driver, "loginFailure");
		} catch (Exception e) {
			softAssert.assertTrue(false, "Exception occurred : " + e.getMessage());
			ScreenShotUtil.takeScreenshot(driver, "loginFailure");
			e.printStackTrace();
		}
		testMethodWrapUp(softAssert, "");
	}

	@Test(enabled = true, description = "This test verifies logout functionality.", priority = 5)
	public void verifyLogout() {
		try {
			commonHeaderMethods.hoverOnUserMenu();
			boolean result = commonHeaderMethods.logout();
			softAssert.assertTrue(result, "Logout unsuccessful. Refer attached screenshot.");
			if (!result)
				ScreenShotUtil.takeScreenshot(driver, "logoutFailure");
		} catch (Exception e) {
			softAssert.assertTrue(false, "Exception occurred : " + e.getMessage());
			ScreenShotUtil.takeScreenshot(driver, "logoutFailure");
			e.printStackTrace();
		}
		testMethodWrapUp(softAssert, "");
	}

}
