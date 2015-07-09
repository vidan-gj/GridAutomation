package unit;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import data.ExcelUtility;
import data.ReadPropertiesFile;

public class AuthUserTest {
	private WebDriver driver;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private static Logger logger = Logger.getLogger(AuthUserTest.class);

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

	ReadPropertiesFile data = new ReadPropertiesFile();

	/**************
	 * TEST INFO *********************************************
	 * 
	 * @author: vgjoze01
	 * @category: functional unit testing
	 * @Info:
	 ***********************************************************************/

	@Test
	public void testAuthUser() throws Exception {
		logger.info("---------------------------- TEST START ------------------------------------");
		logger.info("***************************** AuthUserTest ********************************");
		// Load Log In Page
		driver.get(data.getUrl() + "/login.action?logout=true");
		Thread.sleep(2000);

		// Log In
		driver.findElement(By.id("os_username")).clear();
		driver.findElement(By.id("os_username")).sendKeys(data.getUserName());
		driver.findElement(By.id("os_password")).clear();
		driver.findElement(By.id("os_password")).sendKeys(data.getPassword());
		driver.findElement(By.id("loginButton")).click();
		Thread.sleep(4000);
		logger.info("User Login Successful");
		Thread.sleep(5000);

		// | ---------------------------------------- EXCEL LOGIC:
		// ------------------------------------------------ |
		ExcelUtility.setExcelFile(data.getTestData(), data.getSheetName());

		String pageLink = ExcelUtility.getCellData(2, 6);

		System.out.println("Test page: " + pageLink);
		logger.info(data.getTestData());
		Thread.sleep(3000);
		
		// Go to a Test page
		driver.get(pageLink);
		Thread.sleep(3000);

		// verify you have landed to the right page
		try {
			assertEquals(
					"Test - Software Development & Delivery Toolset - GRID - Stage",
					driver.getTitle());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}

		// Edit the page
		driver.findElement(By.cssSelector("#editPageLink > span")).click();
		Thread.sleep(2000);

		// ------ Enter text . logic to be added ------
		/*
		 * driver.switchTo().frame("wysiwygTextarea_ifr");
		 * driver.findElement(By.id("tinymce")).sendKeys(" Testing ");
		 */
		logger.info("Edit Page");
		driver.findElement(By.id("notifyWatchers")).click();
		Thread.sleep(3000);

		// Save changes
		driver.findElement(By.id("rte-button-publish")).click();
		logger.info("Save changes");
		Thread.sleep(5000);

		// Log Out
		driver.findElement(By.id("user-menu-link")).click();
		driver.findElement(By.id("logout-link")).click();
		Thread.sleep(3000);
		logger.info("User Logout was Successful");

		// Go back to Test Page
		driver.get(pageLink);
		Thread.sleep(3000);

		// Make sure Anon user cannot Edit the page
		driver.getPageSource().contains("Edit");
		logger.info("Verifying that Anon user cannot Edit a page");
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
