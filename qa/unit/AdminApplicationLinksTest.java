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

public class AdminApplicationLinksTest {
	private WebDriver driver;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private static Logger logger = Logger.getLogger(AdminApplicationLinksTest.class);
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
	@Info: Verify the Application Link and check the sync with Jira issues
			(in a Test page). Check the Outgoing and Incoming Authentication
			and validate the Status value for each: Trusted, OAuth, and Basic.
	 ***********************************************************************/

	@Test
	public void testAdminApplicationLinks() throws Exception {
		logger.info("---------------------------- TEST START ------------------------------------");
		logger.info("***************************** AdminApplicationLinksTest ********************************"); 
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

		String pageLink = ExcelUtility.getCellData(2, 6);
		String ticket1 = ExcelUtility.getCellData(8, 1);

		System.out.println("Test page: " + pageLink);
		System.out.println("Date: " + strTodaysDate);
		System.out.println(ticket1);

		// Test Page
		driver.get(pageLink);

		// Validate existing Jira Issue Filter
		assertEquals(ticket1, driver.findElement(By.linkText(ticket1)).getText());
		Thread.sleep(2000);
		logger.info("Validation of Jira Issue Filter was successful");
		
		// Edit Page
		driver.findElement(By.cssSelector("u")).click();

		// Insert more content
		driver.findElement(By.id("rte-button-insert")).click();

		// Jira/Issue Filter
		driver.findElement(By.id("jiralink")).click();
		driver.findElement(By.cssSelector("#jira-connector > div.dialog-components > ul.dialog-page-menu > li.page-menu-item.selected > button.item-button")).click();
		Thread.sleep(2000);

		// Test Search feature of existing Jira issue
		driver.findElement(By.name("jiraSearch")).clear();
		driver.findElement(By.name("jiraSearch")).sendKeys(ticket1);
		driver.findElement(By.name("jiraSearch")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		logger.info("Testing Search feature of Jira existing issues");
		
		// Validate results
		//assertEquals(ticket1, driver.findElement(By.cssSelector("td.issue-key-column > span")).getText());

		// Create New Issue
		//driver.findElement(By.cssSelector("#jira-connector > div.dialog-components > ul.dialog-page-menu > li.page-menu-item > button.item-button")).click();
		driver.findElement(By.xpath("//div[@id='jira-connector']/div/ul/li[2]/button")).click();

		/*    driver.findElement(By.id("select2-drop-mask")).click();
    Thread.sleep(2000);
    driver.findElement(By.id("select2-drop-mask")).click();*/
		Thread.sleep(2000);
		driver.findElement(By.linkText("Cancel")).click();
		Thread.sleep(4000);
		driver.findElement(By.id("rte-button-cancel")).click();
		Thread.sleep(2000);

		// ------------------------------------------------------------

		// Admin Tab
		driver.findElement(By.xpath("//a[@id='admin-menu-link']/span")).click();
		Thread.sleep(3000);

		// Select General Configuration
		driver.findElement(By.id("administration-link")).click();
		Thread.sleep(2000);

		// Enter Admin password
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(data.getPassword());
		driver.findElement(By.id("authenticateButton")).click();
		Thread.sleep(4000);
		logger.info("User Login as Admin was Successful");
		
		// Open Application Links
		driver.findElement(By.id("configure-application-links")).click();
		Thread.sleep(3000);

		// Confluence -> Edit --------------------------------------------------------------------
		driver.findElement(By.linkText("Edit")).click();
		Thread.sleep(3000);
		
		// OPEN Outgoing Authentication
		driver.findElement(By.id("menu-outgoing-authentication-page")).click();
		Thread.sleep(3000);
		logger.info("Validating Outgoing and Incoming Authentication");
		
		// switch to outer frame
		driver.switchTo().frame("outgoing-auth");

		// switch to inner frame
		driver.switchTo().frame("trustedAppsAuthenticationProviderPluginModule");

		// Trusted Applications
		driver.findElement(By.id("login-form-username")).clear();
		driver.findElement(By.id("login-form-username")).sendKeys(data.getUserName());
		driver.findElement(By.id("login-form-password")).clear();
		driver.findElement(By.id("login-form-password")).sendKeys(data.getPassword());
		driver.findElement(By.id("login-form-submit")).click();
		Thread.sleep(3000);

		driver.findElement(By.id("login-form-authenticatePassword")).clear();
		driver.findElement(By.id("login-form-authenticatePassword")).sendKeys(data.getPassword());
		driver.findElement(By.id("login-form-submit")).click();
		Thread.sleep(4000);

		assertEquals("Configured", driver.findElement(By.cssSelector("span.field-value.status-configured")).getText());

		// Open OAuth tab

		// Switch to default window (exit all iframes)
		driver.switchTo().defaultContent();

		// Switch to outer frame
		driver.switchTo().frame("outgoing-auth");

		driver.findElement(By.cssSelector("#config-tab-1 > strong")).click();
		Thread.sleep(3000);

		// Switch to inner frame
		driver.switchTo().frame("OAuthAuthenticatorProviderPluginModule");

		Thread.sleep(3000);
		assertEquals("Not Configured", driver.findElement(By.cssSelector("span.field-value.status-not-configured")).getText());

		// Open Basic Access tab

		// Switch to default window (exit all iframes)
		driver.switchTo().defaultContent();

		// Switch to outer frame
		driver.switchTo().frame("outgoing-auth");

		// Basic Access tab
		driver.findElement(By.cssSelector("#config-tab-2 > strong")).click();

		Thread.sleep(3000);

		// Switch to inner iframe
		driver.switchTo().frame("BasicAuthenticationProviderPluginModule");

		//-- Assert "Not Configured" appears after successful login
		assertEquals("Not Configured", driver.findElement(By.cssSelector("span.field-value.status-not-configured")).getText());


		// OPEN Incoming Authentication

		// Switch to default window (exit all iframes)
		driver.switchTo().defaultContent();

		// Incoming Authentication
		driver.findElement(By.id("menu-incoming-authentication-page")).click();
		//driver.findElement(By.cssSelector("strong")).click();

		// switch to outer frame
		driver.switchTo().frame("incoming-auth");

		// switch to inner frame
		driver.switchTo().frame("trustedAppsAuthenticationProviderPluginModule");
		Thread.sleep(3000);
		assertEquals("Configured", driver.findElement(By.cssSelector("span.field-value.status-configured")).getText());

		// Switch to default window (exit all iframes)
		driver.switchTo().defaultContent();

		// Switch to outer frame
		driver.switchTo().frame("incoming-auth");

		// OAuth tab
		driver.findElement(By.cssSelector("#config-tab-1 > strong")).click();
		Thread.sleep(3000);

		// Switch to inner frame
		driver.switchTo().frame("OAuthAuthenticatorProviderPluginModule");
		Thread.sleep(3000);
		assertEquals("Not Configured", driver.findElement(By.cssSelector("span.field-value.status-not-configured")).getText());

		// Open Basic Access tab

		// Switch to default window (exit all iframes)
		driver.switchTo().defaultContent();

		// Switch to outer frame
		driver.switchTo().frame("incoming-auth");
		driver.findElement(By.cssSelector("#config-tab-2 > strong")).click();
		Thread.sleep(3000);

		// Switch to inner iframe
		driver.switchTo().frame("BasicAuthenticationProviderPluginModule");
		Thread.sleep(5000);

		//-- Assert "Not Configured" appears after successful login
		assertEquals("Not Configured", driver.findElement(By.cssSelector("span.field-value.status-not-configured")).getText());

		Thread.sleep(4000);

		// Switch to default window (exit all frames)
		driver.switchTo().defaultContent();
		driver.findElement(By.linkText("Close")).click();
		// ---------------------------------------------------------------------------------------------//    
		driver.switchTo().defaultContent();
		Thread.sleep(6000);

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
