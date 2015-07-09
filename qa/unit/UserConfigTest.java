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

import data.ReadPropertiesFile;

public class UserConfigTest {
	private WebDriver driver;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private static Logger logger = Logger.getLogger(UserConfigTest.class);
	
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

	/************************** TEST INFO **********************************
	@author: vgjoze01
	@category: functional unit testing
	@Info: Test is covering user functionality, verified by a Confluence 
			admin user. Fields covered are all documented in the comments. 
	 ***********************************************************************/

	@Test
	public void testUserConfig() throws Exception {
		logger.info("---------------------------- TEST START ------------------------------------");
		logger.info("***************************** UserConfigTest ********************************");
		driver.get(data.getUrl() + "/dashboard.action");
		Thread.sleep(2000);
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

		// Admin Login & General Config
		driver.findElement(By.id("com-atlassian-confluence")).click();
		driver.findElement(By.id("admin-menu-link")).click();
		driver.findElement(By.id("administration-link")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(data.getPassword());
		driver.findElement(By.id("authenticateButton")).click();
		Thread.sleep(4000);

		/*
     CONFIGURATION:
		 */

		// Assert Server Base URl [enter value in table]
		assertEquals("Server Base URL", driver.findElement(By.id("editbaseurl-label")).getText());
		assertEquals(data.getUrl(), driver.findElement(By.id("editbaseurl")).getText());
		Thread.sleep(2000);
		logger.info("Assert the Server Base URL");

		// Go to Backup Administration and verify "Perform backups" is Disabled
		driver.findElement(By.linkText("Backup Administration")).click();
		assertEquals("Perform backups", driver.findElement(By.id("dailyBackupStatus-label")).getText());
		assertEquals("Disabled", driver.findElement(By.id("dailyBackupStatus")).getText());
		Thread.sleep(2000);
		logger.info("Verifying that Backup Administration: Perform backups is Dissabled");

		// Go to External Gadgets and verify all 4 links have been added
		driver.findElement(By.id("configure-gadgets")).click();
		assertEquals("Added Gadgets", driver.findElement(By.cssSelector("#gadgets > h2")).getText());
		assertEquals("https://jira.bsc.bscal.com/rest/gadgets/1.0/g/com.almworks.jira.structure:structure-gadget/gadgets/structure-gadget.xml", driver.findElement(By.cssSelector("td.url")).getText());
		assertEquals("https://jira.bsc.bscal.com/rest/gadgets/1.0/g/com.pyxis.greenhopper.jira:greenhopper-gadget-sprint-days-remaining/gadgets/greenhopper-sprint-days-remaining.xml", driver.findElement(By.xpath("//table[@id='addedgadgets']/tbody/tr[2]/td")).getText());
		assertEquals("https://jira.bsc.bscal.com/rest/gadgets/1.0/g/com.pyxis.greenhopper.jira:greenhopper-gadget-sprint-burndown/gadgets/greenhopper-sprint-burndown.xml", driver.findElement(By.xpath("//table[@id='addedgadgets']/tbody/tr[3]/td")).getText());
		assertEquals("https://jira.bsc.bscal.com/rest/gadgets/1.0/g/com.pyxis.greenhopper.jira:greenhopper-gadget-rapid-view/gadgets/greenhopper-rapid-view.xml", driver.findElement(By.xpath("//table[@id='addedgadgets']/tbody/tr[4]/td")).getText());
		Thread.sleep(3000);
		logger.info("Check all 4 links under External Gadgets");

		// Access Mail Servers and test Send email  feature
		driver.findElement(By.linkText("Mail Servers")).click();
		Thread.sleep(2000);
/*		driver.findElement(By.linkText("Send test email")).click();
		Thread.sleep(3000);
		driver.findElement(By.name("confirm")).click();
		Thread.sleep(2000);
		logger.info("Testing Send Email feature ");*/
		
		/*
    ADMINISTRATION:
		 */
		
		logger.info("Accessing the Administration Configuration");
		// Access Scheduled Jobs
		driver.findElement(By.linkText("Scheduled Jobs")).click();
		Thread.sleep(2000);
		logger.info("Checking all Scheduled Jobs");

		// "Back Up Confluence: Disabled" 
		assertEquals("Back Up Confluence", driver.findElement(By.cssSelector("td")).getText());
		assertEquals("Disabled", driver.findElement(By.xpath("//table[@id='schedule-admin']/tbody/tr/td[2]")).getText());
		Thread.sleep(2000);
		logger.info("Checking that Back Up Confluence option is Disabled");

		// "Check Cluster Safety : Scheduled"
		assertEquals("Scheduled", driver.findElement(By.xpath("//table[@id='schedule-admin']/tbody/tr[2]/td[2]")).getText());
		logger.info("Verifying that Check Cluster Safety: Scheduled");
		
		// "Send Recommended Updates Email : Scheduled"
		assertEquals("Scheduled", driver.findElement(By.xpath("//table[@id='schedule-admin']/tbody/tr[18]/td[2]")).getText());
		Thread.sleep(2000);
		logger.info("Verifying that Send Recomended Updates Email: Scheduled");
		
		// Application Links
		driver.findElement(By.id("configure-application-links")).click();
		assertEquals("JIRA", driver.findElement(By.cssSelector("td.ual-cell.application-type")).getText());
		Thread.sleep(2000);

		/*
    USERS & SECURITY:
		 */

		logger.info("Accessing Users & Security Configuration");
		// Global Permissions
		driver.findElement(By.linkText("Global Permissions")).click();
		Thread.sleep(2000);
		logger.info("Validating Global Permissions");
		
		// Validate permissions for confluence-administrators
		assertEquals("confluence-administrators", driver.findElement(By.cssSelector("td")).getText());
		assertEquals("can use", driver.findElement(By.cssSelector("td.permissionCell")).getText());
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr/td[3]/img")).getText());
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr/td[4]/img")).getText());
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr/td[5]/img")).getText());
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr/td[6]/img")).getText());
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr/td[7]/img")).getText());
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr/td[8]/img")).getText());
		logger.info("All Permissions for confluence-administrators have passed");
		
		// Validate permissions for confluence-users
		assertEquals("confluence-users", driver.findElement(By.cssSelector("tr.even > td")).getText());
		assertEquals("can use", driver.findElement(By.cssSelector("tr.even > td.permissionCell")).getText());
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr[2]/td[3]/img")).getText());
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr[2]/td[4]/img")).getText());
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr[2]/td[5]/img")).getText());
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr[2]/td[6]/img")).getText());
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr[2]/td[7]/img")).getText());
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr[2]/td[8]/img")).getText());
		logger.info("All Permissions for confluence-users have passed");
		
		// Validate permissions for Anonymous users
		assertEquals("Anonymous", driver.findElement(By.cssSelector("#aPermissionsTable > tbody > tr > td")).getText());
		assertEquals("can use", driver.findElement(By.cssSelector("#aPermissionsTable > tbody > tr > td.permissionCell")).getText());
		assertEquals("", driver.findElement(By.xpath("//table[@id='aPermissionsTable']/tbody/tr/td[3]/img")).getText());
		Thread.sleep(2000);
		logger.info("All Permissions for Anonymous users have passed");

		// Whitelist
		driver.findElement(By.id("whitelist-menu-link")).click();
		Thread.sleep(2000);

		// Validate "Turn off whitelist" button
		assertEquals("Turn off whitelist", driver.findElement(By.id("whitelist-enable")).getText());
		assertEquals("Application link", driver.findElement(By.xpath("//table[@id='whitelist-table']/tbody[2]/tr/td[2]/span")).getText());
		Thread.sleep(2000);

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
