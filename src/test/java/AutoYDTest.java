
/**
 * File: NopCommerenceTest
 * Authors: Yun-Jiung Wang,Danna Rueda,Daivanshika Patel
 * Description: This is an Automate Test Cases for NopCommerence Demo Store
 * Date: June 12,2025
 */

import java.awt.image.DirectColorModel;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

class AutoYDTest {
	static WebDriver driver;
	static final String LINK = "http:localhost/addressbook/index.php";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		driver = new ChromeDriver();
		driver.get(LINK);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		if (driver != null) {
//			driver.quit();
		}
	}

	@Test
	@Order(1)
	@DisplayName("SmokeTest")
	void smokeTest_getTitle() {
		String title = driver.getTitle();
		System.out.println("title: " + title);
	}

	/**
	 * Mandatory input fields: An person's name or business name must be specified.
	 * At least one of the following must be entered: street/mailing address, email
	 * address, phone number or web site url.
	 */

	/**
	 * Please write all of your test cases below in order
	 */

//	@Test
	@DisplayName("addNewEntry_InvalidTestCases")
	@Order(2)
	void addNewEntry_InvalidTestCases() {
		toAddNewEntry();
		driver.findElement(By.id("addr_first_name")).sendKeys("");
		driver.findElement(By.id("addr_addr_line_1")).sendKeys("Danna");
		driver.findElement(By.xpath("//input[@id='submit_button']")).click();
		driver.findElement(By.tagName("input")).click();
	}

	@Test
	@DisplayName("addNewEntry_Edit")
	@Order(50)
	void valid_edit() {
		toListEntries();
		driver.findElement(By.xpath("//form[@action='./editEntry.php']//input[@type='submit']")).click();

		try {
			fillForm("Fanshawe", "", "", "1001 Fanshawe College", "", "", "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

//	@Test
	@DisplayName("viewDetail")
	@Order(100)
	void clickFirstViewDetail() {
		toListEntries();

		try {
			List<WebElement> viewForms = driver.findElements(By.xpath("//form[@action='./viewEntry.php']"));
			System.out.println("ViewForms. size: " + viewForms.size());
			if (!viewForms.isEmpty()) {
				viewForms.get(0).findElement(By.xpath(".//input[@type='submit']")).click();
				System.out.println("Clicked the first 'View Details' button.");
			} else {
				System.err.println("No 'View Details' forms found.");
				return;
			}
		} catch (Exception e) {
			System.err.println("Error while clicking 'View Details': " + e.getMessage());
			return;
		}

		driver.findElement(By.linkText("Return")).click();
		driver.findElement(By.linkText("Return to Menu")).click();
	}

	/**
	 * This method is directing the index page to addNewEntry web page
	 * 
	 */
	private void toAddNewEntry() {
		driver.findElement(By.linkText("Add New Entry")).click();
		System.out.println("addNewEntry Link: " + driver.getCurrentUrl());
	}

	/**
	 * This method is directing the index page to List All Entries web page
	 * 
	 */
	private void toListEntries() {
		driver.findElement(By.linkText("List All Entries")).click();
		System.out.println("List All Entries Link: " + driver.getCurrentUrl());
	}

	/**
	 * Fill into the mandatory fields
	 * 
	 * @param firstName
	 * @param lastName
	 * @param business
	 * @param addr1
	 * @param email1
	 * @param phone1
	 * @param web1
	 * @param fiedlMap - this is using for putting other not mandatory field element id and values 
	 * @throws Exception
	 */
	private void fillForm(String firstName, String lastName, String business, String addr1, String email1,
			String phone1, String web1, Map<String, String> fieldMap) throws Exception {

		Map<String, String> map = Map.of("addr_first_name", firstName, "addr_last_name", lastName, "addr_business",
				business, "addr_addr_line_1", addr1, "addr_email_1", email1, "addr_phone_1", phone1, "addr_web_url_1",
				web1);

		if (fieldMap != null && !fieldMap.isEmpty()) {
			map.putAll(fieldMap);
		}

		try {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				driver.findElement(By.id(entry.getKey())).sendKeys(entry.getValue());
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			throw new Exception(e.getMessage());
		}
		System.out.println("Successfully fill into the form");

		driver.findElement(By.xpath("//input[@id='submit_button']")).click();
		driver.findElement(By.tagName("input")).click();
	}
}
