package pagemethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pageobjects.ProfilePageObjects;
import utilities.WaitUtil;

public class ProfilePageMethods {
	private WebDriver driver;
	private ProfilePageObjects profilePageObjects;
	private GenericFunctions genericFunctions;

	public ProfilePageMethods(WebDriver driver) {
		this.driver = driver;
		genericFunctions = new GenericFunctions(driver);
		profilePageObjects = PageFactory.initElements(driver, ProfilePageObjects.class);
	}
	
	public boolean isProfilePageVisible() {
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, profilePageObjects.getProfilePageDiv());
		return genericFunctions.isElementVisible(profilePageObjects.getProfilePageDiv());
	}

	public String getFirstName() {
		String firstName = "";
		firstName = firstName.concat(profilePageObjects.getFirstName().getText().trim());
		return firstName;
	}

	public String getLastName() {
		String lastName = "";
		lastName = lastName.concat(profilePageObjects.getLastName().getText().trim());
		return lastName;
	}

}
