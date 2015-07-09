package unit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import data.ExcelUtility;
import data.ReadPropertiesFile;

public class InactiveUsersMacroTest {
	private WebDriver driver;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private static Logger logger = Logger.getLogger(InactiveUsersMacroTest.class);
	
	ReadPropertiesFile data = new ReadPropertiesFile();

	String chromeD = data.getChromeDriver();

	@Before
	public void setUp() throws Exception {
		// Load the Web Driver
		driver = data.getBrowser();
		// Maximize the browser's window
		driver.manage().window().maximize();
		// Implicit wait 
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Initialize log4j properties file
		PropertyConfigurator.configure("log4j.properties");
	}

	/************************** TEST INFO **********************************
	@author: vgjoze01
	@category: functional unit testing
	@Info: This test validates two things:
		1. Inactive users Macro: "confluence-users" group:
			- take 1 user from data table and check if present 
			  in the User table
	 	2. Current date:
	 		- Takes the date + time for a particular user, reformats it
	 		  and compares the value against the current date.
	 ***********************************************************************/

	@Test
	public void testInactiveUsersMacro() throws Exception {
		logger.info("---------------------------- TEST START ------------------------------------");
		logger.info("***************************** InactiveUsersMacroTest ********************************");
		driver.get(data.getUrl() + "/dashboard.action");
		driver.findElement(By.id("login-link")).click();
		Thread.sleep(2000);

		// Log In
		driver.findElement(By.id("os_username")).clear();
		driver.findElement(By.id("os_username")).sendKeys(data.getUserName());
		driver.findElement(By.id("os_password")).clear();
		driver.findElement(By.id("os_password")).sendKeys(data.getPassword());
		driver.findElement(By.id("loginButton")).click();
		Thread.sleep(4000);
		logger.info("User Login Successful");

		// DATE Logic:
		Date cur_date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");  // modify the format as needed
		String strTodaysDate = dateFormat.format(cur_date);

		// | ---------------------------------------- EXCEL LOGIC: ------------------------------------------------ |
		ExcelUtility.setExcelFile(data.getTestData(), data.getSheetName());

		String pageLink = ExcelUtility.getCellData(7, 6);


		System.out.println("Test page: " + pageLink);
		System.out.println("Date: " + strTodaysDate);
		
		// Go to Confluence - inactive users macro
		driver.get(pageLink);
		Thread.sleep(4000);

		// Assert the Page Title
		assertEquals("Confluence - inactive users macro - SCRM Team Private Space - GRID - Stage", driver.getTitle());

		// Assert user present (username)
		assertEquals(data.getUserName(), driver.findElement(By.xpath("//div[@id='main-content']/div/table/tbody/tr[675]/td[2]")).getText());

		// Validate Date:
		// Capture whole date format into a string
		String lastLoginDate = driver.findElement(By.xpath("//div[@id='main-content']/div/table/tbody/tr[675]/td[3]")).getText();
		logger.info("Validating the date");
		
		//Split the string and store in an array
		String[] stringSplit = lastLoginDate.split("\\s+");

		//take the first value (index 0)
		String curDate= stringSplit[0];

		// Print both values
		System.out.println("todays date: " + strTodaysDate);
		System.out.println("splitted value: " + curDate);


		// Compare both dates
		if (strTodaysDate.equals(curDate)) {
			System.out.println("Dates match. User: " + data.getUserName() + ", last time has logged in on: " + curDate);
		}	else {
			System.out.println("User Login Dates mismatch!");
		}



		// Log Out
		driver.findElement(By.id("user-menu-link")).click();
		driver.findElement(By.id("logout-link")).click();
		logger.info("User Logout was Successful");
		logger.info("----------------------------- TEST END -------------------------------------");
	}


	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
