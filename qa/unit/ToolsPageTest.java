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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import data.ReadPropertiesFile;
import api.RequestMethod;
import api.URLStatusChecker;


public class ToolsPageTest {
	private WebDriver driver;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private static Logger logger = Logger.getLogger(ToolsPageTest.class);
	
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
  	@Info: Test is covering the navigation of the main shortcuts to a page:
  	 		Edit, Copy, Watch, Share, Export, Move. At the end all changes
  	 		are reverted back to the original starting point.
	 ***********************************************************************/

	@Test
	public void testToolsPageTest() throws Exception {
		logger.info("---------------------------- TEST START ------------------------------------");
		logger.info("***************************** ToolsPageTest ********************************");
		
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

		// Create a New Page
		driver.findElement(By.id("com-atlassian-confluence")).click();
		Thread.sleep(2000);

		driver.findElement(By.cssSelector("#create-page-button > span")).click();
		logger.info("Creating a new Page");
		
		// Logic:
		WebElement blankPage = driver.findElement(By.cssSelector("div.template-name")); 
		Actions doubleClickAction = new Actions(driver);
		doubleClickAction.moveToElement(blankPage).doubleClick().build().perform();
		Thread.sleep(3000);

		// Enter Page Title
		driver.findElement(By.id("content-title")).clear();
		driver.findElement(By.id("content-title")).sendKeys("Test Please Ignore: ToolsPageTest");
		driver.findElement(By.id("rte-button-publish")).click();
		Thread.sleep(3000);

		// Assert Page Title
		assertEquals("Test Please Ignore: ToolsPageTest - SCRM - GRID - Stage", driver.getTitle());
		logger.info("Asserting new Page Title");
		
		// Test Edit Page
		driver.findElement(By.cssSelector("u")).click();

		// Enter text -- **Doesn't work yet, need more testing to be done...**
		/*    driver.switchTo().frame("wysiwygTextarea_ifr");
    driver.findElement(By.cssSelector("body")).sendKeys("Testing Edit + ");*/

		// disable notify watchers check-box
		driver.findElement(By.id("notifyWatchers")).click();

		driver.findElement(By.id("rte-button-publish")).click();
		Thread.sleep(3000);
		logger.info("Testing main Page functionality");
		
		// Test Watch option 
		driver.findElement(By.cssSelector("#watch-content-button > span")).click();
		// select Watch page
		driver.findElement(By.id("cw-watch-page")).click();
		Thread.sleep(2000);
		// de-select Watch page
		driver.findElement(By.id("cw-watch-page")).click();
		driver.findElement(By.id("main")).click();
		Thread.sleep(2000);

		// Test Share option --> Update: Share option not awailable in new version of Grid
/*		driver.findElement(By.cssSelector("#shareContentLink > span")).click();
		Thread.sleep(2000);


		// ----------------- continue from here  ------------------    
		driver.findElement(By.id("users")).sendKeys(data.getUserName());
		driver.findElement(By.id("users")).sendKeys(Keys.ENTER);
		Thread.sleep(2000);
		driver.findElement(By.id("users")).sendKeys(Keys.ENTER);

		driver.findElement(By.cssSelector("input.button.submit")).click();
		Thread.sleep(4000);*/

		/*    
    // Export to PDF
    // More Options
    driver.findElement(By.xpath("//a[@id='action-menu-link']/span/span")).click();
    Thread.sleep(2000);



    driver.findElement(By.cssSelector("#action-export-pdf-link > span")).click();

    Alert alert = driver.switchTo().alert();
    alert.accept();
    Thread.sleep(3000);*/

		// Copy the Test Page
		driver.findElement(By.xpath("//a[@id='action-menu-link']/span/span")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("#action-copy-page-link > span")).click();
		driver.findElement(By.id("rte-button-publish")).click();
		Thread.sleep(4000);
		logger.info("Make a copy of the Test page");

		// Assert that the page was successfully copied
		assertEquals("Copy of Test Please Ignore: ToolsPageTest - SCRM - GRID - Stage", driver.getTitle());

		// Delete the Page Copy
		driver.findElement(By.xpath("//a[@id='action-menu-link']/span/span")).click();
		driver.findElement(By.cssSelector("#action-remove-content-link > span")).click();
		driver.findElement(By.id("confirm")).click();
		Thread.sleep(3000);
		logger.info("Delete the Page Copy");
		Thread.sleep(8000);
		/*    driver.findElement(By.id("quick-search-query")).clear();
    driver.findElement(By.id("quick-search-query")).sendKeys("Test Please Ignore: ToolsPageTest-grd4");
    Thread.sleep(2000);
    driver.findElement(By.id("quick-search-query")).sendKeys(Keys.ENTER);
    Thread.sleep(3000);
    driver.findElement(By.xpath("//div[@id='main']/div[2]/section/div/div[2]/ol/li/h3/a/strong[3]")).click();*/

		// Logic for deleting the original Test Page
		driver.navigate().back();
		driver.navigate().back();
		driver.navigate().back();
		driver.navigate().back();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@id='action-menu-link']/span/span")).click();
		driver.findElement(By.cssSelector("#action-remove-content-link > span")).click();
		driver.findElement(By.id("confirm")).click();
		Thread.sleep(3000);
		logger.info("Delete the original Test Page");
		
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
