import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BasicClass {
	public static WebDriver driver;
	static final String LINK = "http:localhost/addressbook/index.php";
//	static String DRIVER_PATH = "C:\\Users\\virginia\\OneDrive\\Desktop\\AutomateTestNG_mine\\AddressBook-Selenium\\Driver\\chromedriver.exe";
//	static String DRIVER_PATH = "C:\\Users\\virginia\\chromedriver-win64\\chromedriver.exe";
	static String DRIVER_PATH = System.getProperty("user.dir") + "\\Driver\\chromedriver.exe";

//	static String SCREENSHOT_PATH = "C:\\Users\\virginia\\OneDrive\\Desktop\\AutomateTestNG_mine\\AddressBook-Selenium\\Screenshots\\";
	static String SCREENSHOT_PATH = System.getProperty("user.dir") + File.separator + "Screenshots" + File.separator;

	public static void Launch() {
		System.out.println("Lunch is here");// for debug only
		System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
		driver = new ChromeDriver();
//		driver.get(LINK);
	}

	public void takeScreenshot(String testMethodName) throws IOException {
		File srcFiles = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String filepath = (SCREENSHOT_PATH + testMethodName + ".png");
		try {
			FileUtils.copyFile(srcFiles, new File(filepath));
			System.out.println("Screenshot saved:" + filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
