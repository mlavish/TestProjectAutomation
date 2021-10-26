package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pagemethods.CommonHeaderMethods;
import pagemethods.GenericFunctions;
import pagemethods.LoginPageMethods;
import pagemethods.ProfilePageMethods;
import utilities.Config;
import utilities.ScreenShotUtil;
import utilities.WaitUtil;

public class ProfilePage extends BaseTest {
	ProfilePageMethods profilePageMethods;
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
		profilePageMethods = new ProfilePageMethods(driver);
		commonHeaderMethods = new CommonHeaderMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		config = new Config();
		appUrl = config.fetchConfig("suiterun.properties", "appUrl");
	}

	@Test(enabled = true, description = "This test verifies user name on profile page.", priority = 4)
	public void verifyNameOnProfilePage() {
		driver.get(appUrl);
		try {
			WaitUtil.sleep(5000);
			if (!commonHeaderMethods.isUserLoggedIn()) {
				String email = config.fetchConfig("suiterun.properties", "loginUserEmail");
				String password = config.fetchConfig("suiterun.properties", "loginUserPassword");
				loginPageMethods.login(email, password);
				Assert.assertTrue(commonHeaderMethods.isUserLoggedIn(), "Test case terminated due to login failure.");
			}
			commonHeaderMethods.hoverOnUserMenu();
			commonHeaderMethods.clickProfileLink();
			Assert.assertTrue(profilePageMethods.isProfilePageVisible(), "Profile page failed to load.");
			String actualNameOnProfilePage = profilePageMethods.getFirstName().concat("_")
					.concat(profilePageMethods.getLastName());
			String expectedName = config.fetchConfig("suiterun.properties", "userName");
			boolean result = actualNameOnProfilePage.equalsIgnoreCase(expectedName);
			softAssert.assertTrue(result, "Name on profile page not as expected. Expected: " + expectedName
					+ " but found: " + actualNameOnProfilePage + ". Refer attached screenshot.");
			if (!result)
				ScreenShotUtil.takeScreenshot(driver, "nameIncorrectOnProfileFailure");
		} catch (Exception e) {
			softAssert.assertTrue(false, "Exception occurred : " + e.getMessage());
			ScreenShotUtil.takeScreenshot(driver, "nameIncorrectOnProfileFailure");
			e.printStackTrace();
		}
		testMethodWrapUp(softAssert, "");
	}

}
