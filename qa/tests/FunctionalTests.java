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
import pages.ContactListPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SCRMHomePage;
import pages.SpaceToolsPage;
import pages.TempPage;

public class FunctionalTests {

	public WebDriver driver;

	
	@BeforeTest
	public void openBrowser() {
		System.setProperty("webdriver.chrome.driver", "C:/Users/vgjoze01/Downloads/chromedriver_win32/chromedriver.exe");
		//driver = new FirefoxDriver();
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
	}
	
	@Test//(enabled=false)
	@Parameters({ "userName" , "password" })
	public void testLicense_ExpyDate(String userName, String password) throws Exception {
		Reporter.log("--------------------------- Start of testLicense_ExpyDate ---------------------------");
		HomePage homePage = new HomePage(driver);
		driver.get(homePage.openHomePage());
		Assert.assertEquals(homePage.getPageTitle(), "Dashboard - GRID"); // Stage title: "Dashboard - Confluence"; Prod title: "Dashboard - GRID" 
		LoginPage loginPage = homePage.selectLoginLink();
		loginPage.enterUserName(userName);
		loginPage.enterPassword(password);
		loginPage.selectLoginButton();
		homePage.selectHelpMenu();
		Assert.assertTrue(homePage.checkLicense(), "Confluence 5.5.3");
		//homePage.closeHelpMenu();
		//homePage.logOut();
		driver.quit();
		Reporter.log("--------------------------- End of testLicense_ExpyDate ----------------------------");
	}

	@Test
	@Parameters({ "userName" , "password" })
	public void testcheckExpyDate(String userName, String password) throws Exception {
		Reporter.log("--------------------------- Start of testcheckExpyDate ---------------------------");
		HomePage homePage = new HomePage(driver);
		driver.get(homePage.openHomePage());
		Assert.assertEquals(homePage.getPageTitle(), "Dashboard - Confluence"); // Stage title: "Dashboard - Confluence"; Prod title: "Dashboard - GRID" 
		LoginPage loginPage = homePage.selectLoginLink();
		loginPage.enterUserName(userName);
		loginPage.enterPassword(password);
		loginPage.selectLoginButton();
		homePage.selectAddOns();
		//if ("Administrator Access  - Confluence".equals(driver.getTitle())) {
		homePage.enterPassword(password);	
		homePage.selectLoginButton();
		Assert.assertEquals(homePage.getPageTitle(), "Manage add-ons - Confluence");
		homePage.logOut();
		driver.quit();
		Reporter.log("--------------------------- End of testcheckExpyDate ---------------------------");
	}

	@Test(enabled=false)
	@Parameters({ "userName" , "password" })
	public void testUserPermissions(String userName, String password) throws Exception {
		Reporter.log("--------------------------- Start of testUserPermissions ---------------------------");
		HomePage homePage = new HomePage(driver);
		driver.get(homePage.openHomePage());
		Assert.assertEquals(homePage.getPageTitle(), "Dashboard - Confluence"); // Stage title: "Dashboard - Confluence"; Prod title: "Dashboard - GRID" 
		LoginPage loginPage = homePage.selectLoginLink();
		loginPage.enterUserName(userName);
		loginPage.enterPassword(password);
		TempPage tempPage = loginPage.login_TempPage();
		driver.get(tempPage.openTempPage());  //driver.navigate().to("http://jenkins-stage.bscdev.bscal.com/confluence/display/SCRMP/Temp");
		tempPage.editPage();
		//tempPage.enterText();
		tempPage.cancelButton();
		//tempPage.saveButton();
		homePage.logOut();
		//driver.quit();
		Reporter.log("--------------------------- End of testUserPermissions -----------------------------");
		
	}
	
	@Test(enabled=false)
	public void testAnonUserPermissions() throws Exception {
		Reporter.log("--------------------------- Start of testAnonUserPermissions ---------------------------");
		HomePage homePage = new HomePage(driver);
		driver.get(homePage.openHomePage());
		Assert.assertEquals(homePage.getPageTitle(), "Dashboard - Confluence");
		SCRMHomePage scrmHomePage = homePage.selectSCRMSpace();
		Assert.assertEquals(scrmHomePage.getPageTitle(), "SCRM Home - SCRM - Confluence"); //Stage: "SCRM Home - SCRM - Confluence"
		ContactListPage contactListPage = scrmHomePage.openContactListPage();
		contactListPage.editPageCheck();
		Reporter.log("--------------------------- End of testAnonUserPermissions -----------------------------");
	}

}
