package unit;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute.Space;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import data.ReadPropertiesFile;

public class SpaceManipulationTest {
	private WebDriver driver;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private static Logger logger = Logger.getLogger(SpaceManipulationTest.class);
	
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
  	@Info: Test is validating all Spaces in the system with the appropriate
  			permissions. The test is also creating dynamically a new 
  			Space, and then checking the General Configuration and 
  			permissions of the new space. If all Assertions pass, then
  			the newly created Space will be deleted. Only after the 
  			deletion is complete, the test is marked as Pass.
	 ***********************************************************************/

	@Test
	public void testSpaceManipulation() throws Exception {
		logger.info("---------------------------- TEST START ------------------------------------");
		logger.info("***************************** SpaceManipulationTest ********************************");
		// Load Log In Page
		driver.get(data.getUrl() + "/login.action?os_destination=%2Fdashboard.action");
		Thread.sleep(2000);

		// Log In
		driver.findElement(By.id("os_username")).clear();
		driver.findElement(By.id("os_username")).sendKeys(data.getUserName());
		driver.findElement(By.id("os_password")).clear();
		driver.findElement(By.id("os_password")).sendKeys(data.getPassword());
		driver.findElement(By.id("loginButton")).click();
		Thread.sleep(4000);
		logger.info("User Login Successful");
		
		// Spaces tab
		driver.findElement(By.id("com-atlassian-confluence")).click();

		// Space directory
		driver.findElement(By.id("space-menu-link")).click();
		Thread.sleep(3000);

		// Select All Spaces
		driver.findElement(By.id("view-all-spaces-link")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText("All Spaces")).click();
		logger.info("Checking for Spaces present");
		
		// Assert Space: Digumoorthy, Pavan is present
		assertTrue(isElementPresent(By.linkText("Digumoorthy, Pavan")));

		// Assert Space: Facets Configuration is present
		assertTrue(isElementPresent(By.linkText("Facets Configuration")));
		Thread.sleep(2000);

		// Admin Tab
		driver.findElement(By.xpath("//a[@id='admin-menu-link']/span")).click();
		Thread.sleep(4000);
		
		// Select General Configuration
		driver.findElement(By.id("administration-link")).click();
		Thread.sleep(2000);

		// Enter Admin password
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(data.getPassword());
		driver.findElement(By.id("authenticateButton")).click();
		Thread.sleep(4000);

		// Select Space Permissions
		driver.findElement(By.linkText("Space Permissions")).click();
		Thread.sleep(4000);

		// Assert the Page Title
		assertEquals("Space Permissions - GRID - Stage", driver.getTitle());
		logger.info("Assert Page Title");
		
		// Assert that the permissions are for "confluence-users"
		assertEquals("confluence-users", driver.findElement(By.cssSelector("td")).getText());
		logger.info("Assert permissions for confluence-users");
	
		// Assert View check
		assertEquals("", driver.findElement(By.cssSelector("td.permissionCell > img")).getText());
		logger.info("Checking All permissions in the table");
		Thread.sleep(2000);
		
		// Assert Add check
		assertEquals("", driver.findElement(By.cssSelector("td.permissionCell.permissions-group-start > img")).getText());
		Thread.sleep(2000);
		
		// Assert Delete error check
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr[3]/td[4]/img")).getText());
		Thread.sleep(2000);
		
		// Assert Add check
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr[3]/td[5]/img")).getText());
		Thread.sleep(2000);
		
		// Assert Delete error check
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr[3]/td[6]/img")).getText());
		Thread.sleep(2000);
		
		// Assert Add check
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr[3]/td[7]/img")).getText());
		Thread.sleep(2000);
		
		// Assert Delete error check
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr[3]/td[8]/img")).getText());
		Thread.sleep(2000);
		
		// Assert Add check
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr[3]/td[9]/img")).getText());
		Thread.sleep(2000);
		
		// Assert Delete error check
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr[3]/td[10]/img")).getText());
		Thread.sleep(2000);
		
		// Assert Add/Delete error check
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr[3]/td[11]/img")).getText());
		Thread.sleep(2000);
		
		// Assert Delete error check
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr[3]/td[12]/img")).getText());
		Thread.sleep(2000);
		
		// Assert Export check
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr[3]/td[13]/img")).getText());
		Thread.sleep(2000);
		
		// Assert Admin error check
		assertEquals("", driver.findElement(By.xpath("//table[@id='gPermissionsTable']/tbody/tr[3]/td[14]/img")).getText());
		Thread.sleep(3000);
		Thread.sleep(2000);
		
		// Create New Space
		driver.findElement(By.id("space-menu-link")).click();
		driver.findElement(By.id("create-space-header")).click();
		Thread.sleep(2000);

		driver.findElement(By.xpath("//div[@id='create-dialog']/div/div[2]/button")).click();
		Thread.sleep(2000);
		
		// generate a random number
		int rand = 0;
		for (int i = 0; i < 10; i++) {
			rand = (int) (Math.round(Math.random() * 8999) + 10000);
		}

		// Enter Space Name
		driver.findElement(By.name("name")).clear();
		driver.findElement(By.name("name")).sendKeys(rand + "Automation Test Space");

		// Create the new Space
		driver.findElement(By.name("spaceKey")).click();
		driver.findElement(By.name("spaceKey")).sendKeys(Keys.ENTER);
		Thread.sleep(5000);
		logger.info("Creating a new Space");
		
		// Assert the Title of the page (newly created Space)
		//assertEquals("Test Space Home - Test Space - GRID - Stage", driver.getTitle());

		// Admin Tab
		driver.findElement(By.xpath("//a[@id='admin-menu-link']/span")).click();

		// Select General Configuration
		driver.findElement(By.id("administration-link")).click();
		Thread.sleep(2000);

		// Select Space Permissions
		driver.findElement(By.linkText("Space Permissions")).click();
		Thread.sleep(2000);
		logger.info("Checking the new Space Permissions");
		
		// Test Space -> Manage Permissions
		driver.findElement(By.xpath("(//a[contains(text(),'Manage Permissions')])[31]")).click();
		Thread.sleep(2000);

		// Assert Individual Users table
		assertEquals("Individual Users", driver.findElement(By.xpath("//div[@id='space-tools-body']/h2[2]")).getText());

		// Assert all values in the table
		assertTrue(isElementPresent(By.cssSelector("#uPermissionsTable > tbody > tr.space-permission-row > td.permissionCell > img")));

		assertTrue(isElementPresent(By.cssSelector("#uPermissionsTable > tbody > tr.space-permission-row > td.permissionCell.permissions-group-start > img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='uPermissionsTable']/tbody/tr[3]/td[4]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='uPermissionsTable']/tbody/tr[3]/td[5]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='uPermissionsTable']/tbody/tr[3]/td[6]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='uPermissionsTable']/tbody/tr[3]/td[7]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='uPermissionsTable']/tbody/tr[3]/td[8]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='uPermissionsTable']/tbody/tr[3]/td[9]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='uPermissionsTable']/tbody/tr[3]/td[10]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='uPermissionsTable']/tbody/tr[3]/td[11]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='uPermissionsTable']/tbody/tr[3]/td[12]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='uPermissionsTable']/tbody/tr[3]/td[13]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='uPermissionsTable']/tbody/tr[3]/td[14]/img")));

		// Assert Anonymous Access table
		assertEquals("Anonymous Access", driver.findElement(By.xpath("//div[@id='space-tools-body']/h2[3]")).getText());

		// Assert all values in the table
		assertTrue(isElementPresent(By.cssSelector("#aPermissionsTable > tbody > tr.space-permission-row > td.permissionCell > img")));

		assertTrue(isElementPresent(By.cssSelector("#aPermissionsTable > tbody > tr.space-permission-row > td.permissionCell.permissions-group-start > img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='aPermissionsTable']/tbody/tr[3]/td[4]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='aPermissionsTable']/tbody/tr[3]/td[5]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='aPermissionsTable']/tbody/tr[3]/td[6]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='aPermissionsTable']/tbody/tr[3]/td[7]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='aPermissionsTable']/tbody/tr[3]/td[8]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='aPermissionsTable']/tbody/tr[3]/td[9]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='aPermissionsTable']/tbody/tr[3]/td[10]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='aPermissionsTable']/tbody/tr[3]/td[11]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='aPermissionsTable']/tbody/tr[3]/td[12]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='aPermissionsTable']/tbody/tr[3]/td[13]/img")));

		assertTrue(isElementPresent(By.xpath("//table[@id='aPermissionsTable']/tbody/tr[3]/td[14]/img")));
		Thread.sleep(2000);

		// Open Overview tab
		driver.findElement(By.linkText("Overview")).click();

		// Select Delete Space
		driver.findElement(By.linkText("Delete Space")).click();
		logger.info("Deleting the new Space");
		
		driver.findElement(By.id("confirm")).click();
		Thread.sleep(20000);

		// Assert the Deletion is complete
		assertEquals("100", driver.findElement(By.id("percentComplete")).getText());
		Thread.sleep(3000);

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
