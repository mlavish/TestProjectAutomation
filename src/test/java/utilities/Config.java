/**
 * 
 */
package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	public String platform;
	public String propFilePath = System.getProperty("user.dir") + "/src/main/resources/properties/";

	public String fetchConfig(String propFileName, String property) {
		File file = new File(propFilePath.concat(propFileName));
		String result = null;
		InputStream inputStream = null;

		try {
			Properties prop = new Properties();
			inputStream = new FileInputStream(file);
			prop.load(inputStream);
			result = prop.getProperty(property);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}