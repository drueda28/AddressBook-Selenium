
/**
 * File: NopCommerenceTest
 * Authors: Yun-Jiung Wang,Danna Rueda,Daivanshika Patel
 * Description: This is an Automate Test Cases for NopCommerence Demo Store
 * Date: June 12,2025
 */

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
			driver.quit();
		}
	}

	@Test
	@Order(1)
	@DisplayName("SmokeTest")
	void test_getTitle() {
		String title = driver.getTitle();
		System.out.println("title: " + title);
	}

	/**
	 * Mandatory input fields: An person's name or business name must be specified.
	 * At least one of the following must be entered: street/mailing address, email
	 * address, phone number or web site url.
	 */

	/**
	 * Please write all of your test cases below within order
	 */

	@Test
	@DisplayName("addNewEntry_InvalidTestCases")
	@Order(2)
	void addNewEntry_InvalidTestCases() {
		WebElement web = driver.findElement(By.linkText("Add New Entry"));
		System.out.println("addNewEntry_InvalidTestCases Link: " + driver.getCurrentUrl());
		web.click();
		driver.findElement(By.id("addr_first_name")).sendKeys(" ");
		driver.findElement(By.id("addr_addr_line_1")).sendKeys("Danna");
		driver.findElement(By.xpath("//input[@id='submit_button']")).click();
//		driver.findElement(By.xpath("//input[@id='submit_button']")).click();
//		subtnElement

	}

//	@Test
	@DisplayName("addNewEntry_Edit")
	@Order(2)
	void addNewEntry_test_Edit() {

	}

//	@Test
	@Order(1)
	void addNewEntry_test_create2() {
//		System.out.println("Do");
	}
}
