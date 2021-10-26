package utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.screentaker.ViewportPastingStrategy;
import tests.BaseTest;

public class ScreenShotUtil {
	private static File file;

	public static String takeScreenshot(WebDriver driver, String filename) {
		String absolutePath = "";
		try {
			if (driver != null) {
				final Screenshot screenshot = new AShot().shootingStrategy(new ViewportPastingStrategy(500)).takeScreenshot(driver);
				final BufferedImage image = screenshot.getImage();
				System.out.print(filename + " Screenshot : ");
				filename = filename.replaceAll("[^A-Z a-z\\d\\s]", "") + "_" + new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date()) + ".png";
				String destFile = System.getProperty("user.dir") + "\\screenshots\\" + filename;
				System.out.println(destFile);
				file = new File(destFile);
				try {
					ImageIO.write(image, "PNG", file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				absolutePath = file.getAbsolutePath();
				BaseTest.testMethodScreenshots.put(filename, absolutePath);
			}
			return absolutePath;
		} catch (Exception e) {
			absolutePath = "Exception in taking screenshot";
			System.out.println(absolutePath + " " + e.getMessage());
			BaseTest.testMethodScreenshots.put(filename, absolutePath);
			return absolutePath;
		}
	}

	public static void cleanAllScreenShots() {
		File screenshots = new File(System.getProperty("user.dir") + "/screenshots/");
		if (screenshots.exists()) {
			try {
				FileUtils.cleanDirectory(screenshots);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
