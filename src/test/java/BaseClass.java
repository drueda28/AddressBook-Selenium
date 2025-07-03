import java.io.File;

import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseClass {

	public static WebDriver driver;
	static String driverPath;
	static String screenShotPath; 
	static String LINK;
	static final String SUCCESS_MESSAGE = "The address book entry was updated successfully";

	public static void Launch(String baseURL, String driverFolder, String screenShotFolder) {
		LINK = baseURL;
		driverPath = System.getProperty("user.dir") + driverFolder;
		screenShotPath = System.getProperty("user.dir") + File.separator + screenShotFolder + File.separator;

		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.get(LINK);
	}

	public void takeScreenshot(String testMethodName) throws IOException {
		File srcFiles = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String filepath = (screenShotPath + testMethodName + ".png");
		try {
			FileUtils.copyFile(srcFiles, new File(filepath));
			System.out.println("Screenshot saved:" + filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
