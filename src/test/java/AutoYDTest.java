
/**
 * File: AutoYDTest
 * Authors: Yun-Jiung Wang,Danna Rueda,Daivanshika Patel
 * Description: This is an Automate Test Cases for AddressBook
 * Date: June 12,2025
 */

import java.util.HashMap;

//import java.nio.channels.Selector;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.helpers.FieldMapping;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import java.time.Duration;

// Add webdriver parameters, RUN // DEBUG, driver.quit
class AutoYDTest {
	static WebDriver driver;
	static String chromeKey = "webdriver.chrome.driver";
	// Should be changed by each user
	static String chromepath = "C:\\Tools\\chromedriver-win64\\chromedriver.exe";
	static final String LINK = "http:localhost/addressbook/index.php";

	@BeforeSuite
	static void setUpBeforeClass() throws Exception {
		System.setProperty(chromeKey, chromepath);
		driver = new ChromeDriver();
		driver.get(LINK);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		if (driver != null) {
			// should not be in the state of comment after all works were done
//			driver.quit();
//			driver.close();
		}
	}

	/**
	 * This method is directing the web page from index page to addNewEntry web page
	 * 
	 */
	private void toAddNewEntry() {
		driver.get(LINK);
		driver.findElement(By.linkText("Add New Entry")).click();
		System.out.println("addNewEntry Link: " + driver.getCurrentUrl());
	}

	/**
	 * This method is directing the web page from index page to List All Entries web
	 * page
	 * 
	 */
	private void toListEntries() {
		driver.get(LINK);
		driver.findElement(By.linkText("List All Entries")).click();
		System.out.println("List All Entries Link: " + driver.getCurrentUrl());
	}

	/*
	 * Area below are some tool methods,DO NOT REMOVE IT!
	 */
	/**
	 * Fill into the mandatory fields with element id and input value
	 * 
	 * @param firstName
	 * @param lastName
	 * @param businessName
	 * @param addr1
	 * @param email_1
	 * @param phone1
	 * @param web1
	 * @param fiedlMap     - this is using for putting other not mandatory field
	 *                     element id and values
	 * @throws Exception
	 */
	private void fillForm(String firstName, String lastName, String businessName, String addr1, String email_1,
			String phone1, String web1, Map<String, String> fieldMap) throws Exception {

		Map<String, String> newMap = new HashMap<String, String>();
		Map<String, String> map = Map.of("addr_first_name", firstName, "addr_last_name", lastName, "addr_business",
				businessName, "addr_addr_line_1", addr1, "addr_email_1", email_1, "addr_phone_1", phone1,
				"addr_web_url_1", web1);

		// if the other fields are not null or empty, merge it with the mandatory fields
		if (fieldMap != null && !fieldMap.isEmpty()) {
			newMap.putAll(fieldMap);
			newMap.putAll(map);
		}

		try {
			for (Map.Entry<String, String> entry : newMap.entrySet()) {
				WebElement ele = driver.findElement(By.id(entry.getKey()));

				// If the input value is null, do not change anything
				if (entry.getValue() != null) {
					ele.clear();
					ele.sendKeys(entry.getValue());
				}
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			throw new Exception(e.getMessage());
		}
		System.out.println("Successfully fill into the form");

		driver.findElement(By.xpath("//input[@id='submit_button']")).click();
		driver.get(LINK);
	}

	/**
	 * This method is build for drop down selectors to input values by id the
	 * inputMap, in key -put selector id, value -put the test value Note: this
	 * method do not click the save button
	 * 
	 * @param inputMap
	 * @throws Exception
	 */
	void addSelectorValue(Map<String, String> inputMap) throws Exception {
		List<String> selectorIds = List.of("addr_type", "addr_phone_1_type", "addr_phone_2_type", "addr_phone_3_type");

		if (inputMap == null || inputMap.isEmpty())
			throw new IllegalArgumentException("input map should contains value");

		try {
			for (Map.Entry<String, String> entry : inputMap.entrySet()) {
				if (entry.getValue() == null || entry.getValue().isBlank())
					throw new IllegalArgumentException("field value is null or empty");

				if (!selectorIds.contains(entry.getKey()))
					throw new IllegalArgumentException("selector ID not exits");

				Select mySelect = new Select(driver.findElement(By.id(entry.getKey())));
				mySelect.selectByValue(entry.getValue());
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	private String genString(int len) {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder(len);

		for (int i = 0; i < len; i++) {
			int a = new Random().nextInt(chars.length());
			sb.append(chars.charAt(a));
		}

		System.out.println("genString: " + sb);
		return sb.toString();
	}

	private String gebDigits(int len) {
		Random rd = new Random();
		StringBuilder sb = new StringBuilder(len);

		for (int i = 0; i < len; i++) {
			sb.append(Math.abs(rd.nextInt()));
		}
		System.out.println("genDigits: " + sb.toString());
		return sb.toString();
	}

//------------- The end of tool methods --------- Test cases starts from here-----------// 

	@Test
	void smokeTest_getTitle() {
		String title = driver.getTitle();
		System.out.println("title: " + title);
	}

	@Test
	@Parameters("title")
	void smokeTest_parameter(String title) {
		driver.get(LINK);
		assertEquals(driver.getTitle(), title);
	}

	/**
	 * Notes from AddressBook: Mandatory input fields: An person's name or business
	 * name must be specified. At least one of the following must be entered:
	 * street/mailing address, email address, phone number or web site url.
	 */

	/**
	 * Please write all of your test cases below in order
	 */

//	@Test 
	@DisplayName("addNewEntry_InvalidTestCases_FillAddressLine1")
	@Order(2)
	void addNewEntry_InvalidTestCases_FillAddressLine1() {
		toAddNewEntry();
		driver.findElement(By.id("addr_first_name")).sendKeys("");
		driver.findElement(By.id("addr_addr_line_1")).sendKeys("456 Street 3");
		driver.findElement(By.xpath("//input[@id='submit_button']")).click();

		String output = driver.findElement(By.xpath("//p")).getText();
		Assertions.assertEquals("An person's name or business name must be specified.", output);
		// driver.findElement(By.tagName("input")).click();
		// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
	}

//	@Test
	@DisplayName("addNewEntry_InvalidTestCases_FillFirstName")
	@Order(3)
	void addNewEntry_InvalidTestCases_FillFirstName() {
		toAddNewEntry();
		driver.findElement(By.id("addr_first_name")).sendKeys("Mark");
		driver.findElement(By.xpath("//input[@id='submit_button']")).click();

		String output = driver.findElement(By.xpath("//p")).getText();
		Assertions.assertEquals(
				"At least one of the following must be entered: street/mailing address, email address, phone number or web site url.",
				output);
	}

//	@Test
//	@Parameters("addr1")
	void valid_edit(String addr1) {
		toListEntries();
		driver.findElement(By.xpath("//form[@action='./editEntry.php']//input[@type='submit']")).click();
		try {
			System.out.println("Addr1: " + addr1);
			driver.findElement(By.id("addr_business")).sendKeys(addr1);
			driver.findElement(By.xpath("//input[@id='submit_button']")).click();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		driver.get(LINK);
	}

//	@Test
	void edit_clearForm_valid() {
		toListEntries();
		driver.findElement(By.xpath("//form[@action='./editEntry.php']//input[@type='submit']")).click();
		try {
			driver.findElement(By.id("reset_button")).click();
			fillForm("Reset", "", "", "2001 Fanshawe College", "", "", "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

//	@Test
//	void edit_phone1_OnMAX() {
//		toListEntries();
//		driver.findElement(By.xpath("//form[@action='./editEntry.php']//input[@type='submit']")).click();
//		try {
//			fillForm("phoneMax", "", "", "", "", "", "", Map.of("addr_phone_1", "543281495076287492034561"));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.err.println(e.getMessage());
//		}

//	}

//	@Test
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
		driver.get(LINK);
	}

//	@Test
	void edit_Return_valid() {
		toListEntries();
		driver.findElement(By.xpath("//form[@action='./editEntry.php']//input[@type='submit']")).click();
		driver.findElement(By.linkText("Return (Cancel)")).click();
		driver.get(LINK);
	}

//	@Test
	void edit_select_valid() {
		toListEntries();
		driver.findElement(By.xpath("//form[@action='./editEntry.php']//input[@type='submit']")).click();
		try {
			Map<String, String> map = Map.of("addr_type", "Business", "addr_phone_1_type", "Home Fax",
					"addr_phone_2_type", "Work", "addr_phone_3_type", "Work Fax");
			addSelectorValue(map);
			fillForm("select2", "", "", "2213 Fanshawe College", "", "", "", Map.of("addr_phone_1", "(226)3345678",
					"addr_phone_2", "+1 2264431235", "addr_phone_3", "+1 3345567899"));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		driver.get(LINK);
	}

	@Test(priority = 1, dataProvider = "edit_fields_OnMax")
	void edit_fields_OnMax(String key, String value) {
		System.out.println("Key: " + key + " Value: " + value);
		toListEntries();
		driver.findElement(By.xpath("//form[@action='./editEntry.php']//input[@type='submit']")).click();

		try {
			WebElement webElement = driver.findElement(By.id(key));
			webElement.clear();
			webElement.sendKeys(value);
			driver.findElement(By.xpath("//input[@id='submit_button']")).click();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}

		driver.get(LINK);
	}

	@DataProvider(name = "edit_fields_OnMax")
	Object[][] edit_fields_OnMax() {
		return new Object[][] { new Object[] { "addr_first_name", genString(50) }, { "addr_last_name", genString(50) },
				{ "addr_business", genString(75) }, { "addr_addr_line_1", genString(75) },
				{ "addr_city", genString(50) }, { "addr_region", genString(50) }, { "addr_country", genString(50) },
				{ "addr_post_code", genString(20) }, { "addr_email_1", genString(124) + "@com" },
				{ "addr_phone_1", genString(25) }, { "addr_web_url_1", "http://" + genString(121) } };
	}

	@Test
	void edit_webUrl_invalid() {
		toListEntries();

		try {
			fillForm("", "", "WebUrl test", "", "", "", "fanshawe.ca/p=1", null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
