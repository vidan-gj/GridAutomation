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

public class CheckLicenseExpyTest {
	private WebDriver driver;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private static Logger logger = Logger.getLogger(CheckLicenseExpyTest.class);

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

	/************** TEST INFO *********************************************
	@author: vgjoze01
	@category: functional unit testing
	@Info: 
	 ***********************************************************************/


	@Test
	public void testCheckLicenseExpy() throws Exception {
		logger.info("---------------------------- TEST START ------------------------------------");
		logger.info("***************************** CheckLicenseExpyTest ********************************");

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
		
		// | ---------------------------------------- EXCEL LOGIC: ------------------------------------------------ |

		/* NOT WORKING.. EMPTY CELL ERROR..INVESTIGATE FURTHER..	   
  ExcelUtility.setExcelFile(data.getTestData(), data.getSheetName());

    String licensedUsers = ExcelUtility.getCellData(1, 6);
    String expyDate1 = ExcelUtility.getCellData(1, 7);	// SupportExpy Date
    String expyDate2 = ExcelUtility.getCellData(1, 8);	// Plugin Expy Date

    System.out.println("Licensed Users: " + licensedUsers);
    System.out.println("Support Expiration Date: " + expyDate1);
    System.out.println("Plugin Expiration Date: " + expyDate2);*/

		// Admin Menu
		driver.findElement(By.id("admin-menu-link")).click();

		// General Configuration
		driver.findElement(By.id("administration-link")).click();
		Thread.sleep(2000);

		// Admin Log In
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(data.getPassword());
		driver.findElement(By.id("authenticateButton")).click();
		Thread.sleep(2000);

		// License Details
		driver.findElement(By.linkText("License Details")).click();
		logger.info("Checking License Details");
		
		// Assert Licensed Users
		assertEquals("2000", driver.findElement(By.xpath("//div[@id='admin-body-content']/table/tbody/tr[4]/td[2]/strong")).getText());
		Thread.sleep(2000);
		logger.info("Assert the Licensed users is set to: 2000");
		
		// Assert Support Period
		assertTrue(isElementPresent(By.xpath("//div[@id='admin-body-content']/table/tbody/tr[5]/td[2]")));
		assertEquals("Your commercial Confluence support and updates are available until Jun 29, 2015. Learn more", driver.findElement(By.xpath("//div[@id='admin-body-content']/table/tbody/tr[5]/td[2]")).getText());
		logger.info("Assert the Support Period is available until Jun 29, 2015");
		
		// Admin menu > Add-ons
		driver.findElement(By.xpath("//a[@id='admin-menu-link']/span")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("plugin-administration-link")).click();

		// Gliffy plugin
		driver.findElement(By.cssSelector("#upm-plugin-2356306081 > div.upm-plugin-row > span.expander")).click();
		Thread.sleep(2000);
		logger.info("Assert the Gliffy plugin");
		
		// Assert License details: 2000 users
		assertEquals("2000-user commercial license, Standard, expires Oct 14, 2015", driver.findElement(By.cssSelector("div.upm-plugin-license-info.upm-plugin-detail")).getText());

		// Assert License Status: Valid
		assertEquals("Valid", driver.findElement(By.cssSelector("div.upm-plugin-license-status.upm-plugin-detail")).getText());
		logger.info("Assert the License Status is Valid");
		Thread.sleep(5000);
		
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
