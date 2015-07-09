package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.BlankNewPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SCRMHomePage;
import pages.SpaceToolsPage;

public class SmokeTests {

	public WebDriver driver;
	public static final String userName = "vgjoze01";
	public static final String password = "V!d@n12345";
	
	@BeforeTest
	public void openBrowser() {
		System.setProperty("webdriver.chrome.driver", "C:/Users/vgjoze01/Downloads/chromedriver_win32/chromedriver.exe");
		//driver = new FirefoxDriver();
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
	}
	
	@Test(enabled=false)
	@Parameters({"userName" , "password"})
	public void testHomePage(String userName, String password) throws Exception {
		Reporter.log("--------------------------- Start of testHomePage ---------------------------");
		HomePage homePage = new HomePage(driver);
		driver.get(homePage.openHomePage());
		//Assert.assertEquals(homePage.getPageTitle(), "Dashboard - Confluence"); // Stage title: "Dashboard - Confluence"; Prod title: "Dashboard - GRID" 
		LoginPage loginPage = homePage.selectLoginLink();
		loginPage.enterUserName(userName);
		loginPage.enterPassword(password);
		loginPage.selectLoginButton();
		homePage.selectHelpMenu();
		Assert.assertEquals(homePage.checkLicense(), "Confluence 5.5.3");
		homePage.closeHelpMenu();
		Reporter.log("--------------------------- End of testHomePage ---------------------------");
	}

	@Test//(enabled= false) // http://stackoverflow.com/questions/19694507/how-to-handle-popup-window-using-selenium-webdriver-with-java
	//@Parameters({"userName" , "password"})
	//add two input parameters
	public void testCreateNewPage() throws Exception {
		Reporter.log("---------------------------*Start of testCreateNewPage*---------------------------");
		HomePage homePage = new HomePage(driver);
		driver.get(homePage.openHomePage());
		Assert.assertEquals(homePage.getPageTitle(), "Dashboard - GRID");
		LoginPage loginPage = homePage.selectLoginLink();
		loginPage.enterUserName(userName);
		loginPage.enterPassword(password);
		//loginPage.waitPageLoad();
		loginPage.selectLoginButton();
		loginPage.createTab();
		//loginPage.createBlankNewPage();
		loginPage.createButton();
		
		Reporter.log("---------------------------*End of testCreateNewPage*---------------------------");
	}
	
	@Test(enabled=false)
	@Parameters({"userName" , "password"})
	public void testAddTemplates(String userName, String password) throws Exception {
		Reporter.log("---------------------------*Start of testAddTemplates*---------------------------");
		HomePage homePage = new HomePage(driver);
		driver.get(homePage.openHomePage());
		//Assert.assertEquals(homePage.getPageTitle(), "Dashboard - GRID");
		LoginPage loginPage = homePage.selectLoginLink();
		loginPage.enterUserName(userName);
		loginPage.enterPassword(password);
		loginPage.waitPageLoad();
		loginPage.selectLoginButton();
		SpaceToolsPage spaceToolsPage = loginPage.addTemplateLink();
		spaceToolsPage.selectOverview();
		Reporter.log("---------------------------*End of testAddTemplates*---------------------------");
	}

	@Test(enabled=false) // works great, just Firefox displays weird page layout.
	@Parameters({"userName" , "password"})
	public void testOpenSpaces(String userName, String password) throws Exception {
		Reporter.log("---------------------------*Start of testOpenSpaces*---------------------------");
		HomePage homePage = new HomePage(driver);
		driver.get(homePage.openHomePage());
		LoginPage loginPage = homePage.selectLoginLink();
		loginPage.enterUserName(userName);
		loginPage.enterPassword(password);
		loginPage.waitPageLoad();
		loginPage.selectLoginButton();
		SCRMHomePage scrmHomePage = homePage.selectSCRMSpace(); 
		Reporter.log("---------------------------*End of testOpenSpaces*---------------------------");
	}
}
