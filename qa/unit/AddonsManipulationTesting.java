package unit;

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

import data.ReadPropertiesFile;

public class AddonsManipulationTesting {
	private WebDriver driver;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private static Logger logger = Logger.getLogger(AddonsManipulationTesting.class);
	
	ReadPropertiesFile data = new ReadPropertiesFile();


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
	@Info: Validate All Add-ons installed. And check the Calendar functionality.
			To change the browser, go to ReadPropertiesFile.java class and
			only modify the getBrowser() method.
	 ***********************************************************************/

	@Test
	public void testAddonsManipulationTesting() throws Exception {
		logger.info("---------------------------- TEST START ------------------------------------");
		logger.info("***************************** AddonsManipulationTesting ********************************"); 
		driver.get(data.getUrl() + "/dashboard.action");
		driver.findElement(By.id("login-link")).click();
		Thread.sleep(2000);

		// Log In
		driver.findElement(By.id("os_username")).clear();
		driver.findElement(By.id("os_username")).sendKeys(data.getUserName());
		driver.findElement(By.id("os_password")).clear();
		driver.findElement(By.id("os_password")).sendKeys(data.getPassword());
		driver.findElement(By.id("loginButton")).click();
		logger.info("User Login Successful");
		Thread.sleep(4000);

		// Add-ons
		driver.findElement(By.id("com-atlassian-confluence")).click();
		driver.findElement(By.xpath("//a[@id='admin-menu-link']/span")).click();
		Thread.sleep(2000);

		// Log in as Admin
		driver.findElement(By.id("plugin-administration-link")).click();
		driver.findElement(By.id("password")).clear();  
		driver.findElement(By.id("password")).sendKeys(data.getPassword());    
		driver.findElement(By.id("authenticateButton")).click();
		Thread.sleep(4000);

		// Display All Add-ons
		//driver.findElement(By.cssSelector("div.selected-value > li")).click();
		driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[2]/div[3]/div/div/div[10]/div/div[3]/div/div/span")).click();
		Thread.sleep(5000);
		logger.info("Display all Add-ons");
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("li.upm-filter-all")).click();
		Thread.sleep(5000);
		
		// Validate several System Add-ons
		driver.findElement(By.cssSelector("#upm-plugin-3185518088 > div.upm-plugin-row > h4.upm-plugin-name")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("#upm-plugin-3276178523 > div.upm-plugin-row > h4.upm-plugin-name")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("#upm-plugin-3784116367 > div.upm-plugin-row > h4.upm-plugin-name")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("#upm-plugin-1351597521 > div.upm-plugin-row > h4.upm-plugin-name")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("#upm-plugin-3987298950 > div.upm-plugin-row > h4.upm-plugin-name")).click();
		Thread.sleep(3000);
		logger.info("Validating System Add-ons");
		
		// Open Calendars tab
		driver.findElement(By.xpath("//header[@id='header']/nav/div/div[2]/ul/li[3]/a/span")).click();
		Thread.sleep(2000);
		logger.info("Opening Calendars tab");
		Thread.sleep(5000);
		
/*		// Add Event
		driver.findElement(By.id("com-atlassian-confluence")).click();
		Thread.sleep(4000);
		driver.findElement(By.id("add-event-link")).click();
		Thread.sleep(8000);
		driver.findElement(By.id("select2-drop-mask")).click();
*/
		//OK button
		//driver.findElement(By.xpath("/html/body/div[8]/div/div[2]/button")).click();

		// Cancel button
		//driver.findElement(By.xpath("/html/body/div[8]/div/div[2]/a")).click();	

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
