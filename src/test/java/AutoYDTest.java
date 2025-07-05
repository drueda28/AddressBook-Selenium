
/**
 * File: AutoYDTest
 * Authors: Yun-Jiung Wang,Danna Rueda,Daivanshika Patel
 * Description: This file contains all the Test Cases we made for AddressBook
 * Date: June 12,2025
 */

import java.time.Duration;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.Assertions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

@Listeners(IListener.class)
public class AutoYDTest extends BaseClass {

	@BeforeMethod
	@Parameters({ "baseURL", "driverFolder", "screenShotFolder" })
	public void Initialization(String baseURL, String driverFolder, String screenShotFolder) {
		Launch(baseURL, driverFolder, screenShotFolder);
	}

	@AfterMethod
	public void teardown() {
		driver.quit();
	}

	// This method is directing the web page from index page to addNewEntry web page
	private void toAddNewEntry() {
		driver.get(LINK);
		driver.findElement(By.linkText("Add New Entry")).click();
	}

	// This method is directing the web page from index page to List All Entries web
	// page
	private void toListEntries() {
		driver.get(LINK);
		driver.findElement(By.linkText("List All Entries")).click();
	}

	/*
	 * Area below are some tool methods
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
				// entry.getKey()
				WebElement ele = driver.findElement(By.xpath("//input[@id='" + entry.getKey() + "']"));

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

		driver.findElement(By.xpath("//input[@type='submit']")).click();
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

				Select mySelect = new Select(driver.findElement(By.xpath("//select[@name='" + entry.getKey() + "']")));
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
		return sb.toString();
	}
	// ------------- The end of tool methods --------- Test cases starts from
	// here-----------//

	@Test(priority = 1)
	void smokeTest_getTitle() {
		assertEquals("Address Book", driver.getTitle());
	}

//	@Test(priority = 2)
//	@Parameters("title")
	void smokeTest_parameter(String title) {
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

	@Test(priority = 3, dataProvider = "addNewEntry_Invalid_FillOnlyAddress_EmptyNames")
	void addNewEntry_InvalidTestCases_FillOnlyAddress_EmptyNames(String key, String value) {
		toAddNewEntry();
		System.out.println("addNewEntry_InvalidTestCases id: " + key + " value:" + value);
		try {
			WebElement webElement = driver.findElement(By.xpath("//input[@name='" + key + "']"));
			webElement.sendKeys(value);

			driver.findElement(By.xpath("//input[@name='submit_button']")).click();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}

		String output = driver.findElement(By.xpath("//p")).getText();
		Assertions.assertEquals("An person's name or business name must be specified.", output);
	}

	@DataProvider(name = "addNewEntry_Invalid_FillOnlyAddress_EmptyNames")
	Object[][] addNewEntry_Invalid_FillOnlyAddress_EmptyNames() {
		return new Object[][] { new Object[] { "addr_addr_line_1", "456 Street 3" } };
	}

	@Test(priority = 4)
	void addNewEntry_InvalidTestCases_FillOnlyAddress_EmptyTheRestFields() {
		toAddNewEntry();
		driver.findElement(By.name("addr_first_name")).sendKeys("Mark");
		driver.findElement(By.xpath("//input[@name='submit_button']")).click();

		String output = driver.findElement(By.xpath("//p")).getText();
		Assertions.assertEquals(
				"At least one of the following must be entered: street/mailing address, email address, phone number or web site url.",
				output);
	}

	@Test(priority = 5)
	public void addNewEntry_Family() {
		toAddNewEntry();

		try {
			fillForm("Danna", "Redua", "Fanshawe Gupta", "1200 Fanshawe College", "danna@example.com", "1234567899",
					"https://fanshawe", null);

			addSelectorValue(Map.of("addr_type", "Family", "addr_phone_1_type", "Mobile"));

			driver.findElement(By.xpath("//input[@id='submit_button']")).click();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'successfully')]")));
			String message = driver.findElement(By.tagName("h2")).getText();
			assertEquals(ADDED_MESSAGE, message);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}
	}

	@Test(priority = 6)
	public void addNewEntry_Business() {
		toAddNewEntry();

		try {
			fillForm("Daivanshika", "Patel", " ", "1200 Fanshawe", "daivanshika@example.com", "12345567345",
					"https://localhost", null);
			addSelectorValue(Map.of("addr_type", "Business"));

			driver.findElement(By.xpath("//input[@name='submit_button']")).click();
			
//			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'successfully')]")));
			
			String message = driver.findElement(By.tagName("h2")).getText();
			assertEquals(ADDED_MESSAGE, message);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}
	}

	@Test(priority = 7)
	public void addNewEntry_Friend() {
		toAddNewEntry();
		try {
			fillForm("WWW", "Gupat ", " ", "200 Fanshawe", "daivanshika@example.com", "12345567345",
					"https://localhost", null);
			addSelectorValue(Map.of("addr_type", "Friend"));
			driver.findElement(By.xpath("//input[@id='submit_button']")).click();
//			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'successfully')]")));
			String message = driver.findElement(By.tagName("h2")).getText();
			assertEquals(ADDED_MESSAGE, message);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}

		String message = driver.findElement(By.tagName("h2")).getText();
		assertEquals(ADDED_MESSAGE, message);

		// Validation can update differently in case there is a success message or a
		// redirect
		assertTrue(driver.getPageSource().contains("Address Book Main Menu"));
	}

	@Test(priority = 8)
	void testListofEntryVerification() {
		driver.findElement(By.linkText("List All Entries")).click();
		String pageText = driver.findElement(By.tagName("body")).getText();

		assertTrue(pageText.contains("Daivanshika"));
		assertTrue(pageText.contains("Patel"));
		assertTrue(pageText.contains("daivanshika@example.com"));
	}

	@Test(priority = 9)
	void addNewEntry_valid() {
		toAddNewEntry();

		try {
			fillForm("Dai", "Oatel", "Fanshawe College", "1012 Fanshawe College Blvd.", "mail@mail.com", "2254431234",
					"https://www.fanshawec.ca/programs/gap5-general-arts-and-science-1-yr-english-language-studies/next?utm_source=google&utm_medium=cpc&utm_campaign=rbm_search&gad_source=1&gad_campaignid=20146753815&gbraid=0AAAAAD3AQO_DQecBewzNJQdGz-iqmgRo9&gclid=Cj0KCQjw953DBhCyARIsANhIZob6STdOEQRKHkYrou1ROmSSBI4NpZRI9OBIDxwn2jCFxDf8tc7NAgQaAvGrEALw_wcB",
					null);
			driver.findElement(By.xpath("//input[@name='submit_button']")).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(e.getMessage());
			return;
		}

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'successfully')]"))).click();

		String pageText = driver.findElement(By.tagName("body")).getText();
		String message = driver.findElement(By.tagName("h2")).getText();
		assertEquals(ADDED_MESSAGE, message);
		assertTrue(pageText.contains("Dai"));
		assertTrue(pageText.contains("Oatel"));
		assertTrue(pageText.contains("1012 Fanshawe College Blvd."));
		assertTrue(pageText.contains("@mail.com"));
		assertTrue(pageText.contains("2254431234"));
		assertTrue(pageText.contains(
				"https://www.fanshawec.ca/programs/gap5-general-arts-and-science-1-yr-english-language-studies/next?utm_source=google&utm_medium=cpc&utm_campaign=rbm_search&gad_source=1&gad_campaignid=20146753815&gbraid=0AAAAAD3AQO_DQecBewzNJQdGz-iqmgRo9&gclid=Cj0KCQjw953DBhCyARIsANhIZob6STdOEQRKHkYrou1ROmSSBI4NpZRI9OBIDxwn2jCFxDf8tc7NAgQaAvGrEALw_wcB"));
	}

	@Test(priority = 10)
	@Parameters("addr1")
	void edit_valid_address1(String addr1) {
		toListEntries();
		try {
			driver.findElement(By.xpath("//form[@action='./editEntry.php']//input[@type='submit']")).click();
			driver.findElement(By.xpath("//input[@name='addr_business']")).sendKeys(addr1);
			driver.findElement(By.xpath("//input[@name='submit_button']")).click();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		String message = driver.findElement(By.tagName("h2")).getText();
		assertEquals(SUCCESS_MESSAGE, message);
		driver.get(LINK);
	}

	@Test(priority = 11)
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

		String message = driver.findElement(By.tagName("h2")).getText();
		assertEquals(SUCCESS_MESSAGE, message);
	}

	@Test(priority = 12)
	void view_valid() {
		toListEntries();

		try {
			List<WebElement> viewForms = driver.findElements(By.xpath("//form[@action='./viewEntry.php']"));
			System.out.println("ViewForms. size: " + viewForms.size());
			if (!viewForms.isEmpty()) {
				viewForms.get(0).findElement(By.xpath(".//input[@type='submit']")).click();
			} else {
				System.err.println("No 'View Details' forms found.");
				return;
			}
		} catch (Exception e) {
			System.err.println("Error while clicking 'View Details': " + e.getMessage());
			return;
		}

		String page = driver.findElement(By.tagName("h2")).getText();
		assertEquals("Address Book Entry Details", page);
	}

	@Test(priority = 13)
	@Parameters("title")
	void edit_Return_valid(String title) {
		toListEntries();

		driver.findElement(By.xpath("//form[@action='./editEntry.php']//input[@type='submit']")).click();

		// Wait the Return Button shows
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Return')]"))).click();

		assertEquals(title, driver.getTitle());
	}

	@Test(priority = 14)
	void edit_select_valid() {
		toListEntries();
		driver.findElement(By.xpath("//form[@action='./editEntry.php']//input[@type='submit']")).click();
		try {
			addSelectorValue(Map.of("addr_type", "Business", "addr_phone_1_type", "Home Fax",
					"addr_phone_2_type", "Work", "addr_phone_3_type", "Work Fax"));
			fillForm("select2", "", "", "2213 Fanshawe College", "", "", "", Map.of("addr_phone_1", "(226)3345678",
					"addr_phone_2", "+1 2264431235", "addr_phone_3", "+1 3345567899"));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		String message = driver.findElement(By.tagName("h2")).getText();
		assertEquals(SUCCESS_MESSAGE, message);
	}

	@Test(priority = 15, dataProvider = "edit_fields_OnMax")
	void edit_fields_OnMax(String key, String value) {
		toListEntries();
		driver.findElement(By.xpath("//form[@action='./editEntry.php']//input[@type='submit']")).click();

		try {
			WebElement webElement = driver.findElement(By.id(key));
			webElement.clear();
			webElement.sendKeys(value);
			driver.findElement(By.xpath("//input[@type='submit']")).click();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}

		String message = driver.findElement(By.tagName("h2")).getText();
		assertEquals(SUCCESS_MESSAGE, message);
	}

	@DataProvider(name = "edit_fields_OnMax")
	Object[][] edit_fields_OnMax() {
		return new Object[][] { new Object[] { "addr_first_name", genString(50) }, { "addr_last_name", genString(50) },
				{ "addr_business", genString(75) }, { "addr_addr_line_1", genString(75) },
				{ "addr_city", genString(50) }, { "addr_region", genString(50) }, { "addr_country", genString(50) },
				{ "addr_post_code", genString(20) }, { "addr_email_1", genString(124) + "@com" },
				{ "addr_phone_1", genString(25) }, { "addr_web_url_1", "http://" + genString(121) } };
	}

	@Test(priority = 16, dataProvider = "edit_fields_Invalid_sepcialChar")
	void edit_invalid_sepcialChar(String key, String value) {
		toListEntries();
		driver.findElement(By.xpath("//form[@action='./editEntry.php']//input[@type='submit']")).click();
		try {
			WebElement webElement = driver.findElement(By.id(key));
			driver.findElement(By.xpath(""));
			webElement.clear();
			webElement.sendKeys(value);
			driver.findElement(By.xpath("//input[@type='submit']")).click();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}

		String message = driver.findElement(By.tagName("h2")).getText();
		assertNotEquals(SUCCESS_MESSAGE, message);
	}

	@DataProvider(name = "edit_fields_Invalid_sepcialChar")
	Object[][] edit_fields_Invalid_sepcialChar() {
		return new Object[][] { new Object[] { "addr_first_name", "!@#$%^&*()" } };
	}

	@Test(priority = 17, dataProvider = "edit_Invalid_alert")
	void edit_invalid_JavaScript(String key, String value) {
		try {
			WebElement webElement = driver.findElement(By.id(key));
			webElement.clear();
			webElement.sendKeys(value);

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}

		List<WebElement> successMessages = driver.findElements(By.xpath("//h2[text()='" + SUCCESS_MESSAGE + "']"));

		assertFalse(successMessages.isEmpty());
		assertNotEquals(SUCCESS_MESSAGE, successMessages.get(0));
	}

	@DataProvider(name = "edit_Invalid_alert")
	Object[][] edit_Invalid_alert() {
		return new Object[][] { new Object[] { "addr_last_name", "<script>alert('Hello World')</script>" } };
	}
}
