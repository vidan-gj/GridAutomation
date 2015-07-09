package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {

	public WebDriver driver;
	public final static String homePageLink = "https://grid.bsc.bscal.com/dashboard.action";
	
	public HomePage(WebDriver driver) {	
		this.driver = driver;
	}
	/*
	@FindBy(xpath="//*[@id='login-link']")
	private WebElement loginLink;
	**/
	
	//@FindBy(id="help-menu-link")
	//public WebElement helpMenu;
	
	
	public String openHomePage() {
		return homePageLink;
	}
	
	public String getPageTitle() {
		return driver.getTitle();
	}
	
	public LoginPage selectLoginLink() {
		driver.findElement(By.xpath("//*[@id='login-link']")).click();
		return new LoginPage(driver);
	}

	public void selectSpacesLink() {
		driver.findElement(By.xpath("//*[@id='space-menu-link']/span[1]")).click();
	}

	public SCRMHomePage selectSCRMSpace() {
		driver.findElement(By.xpath("//*[@id='spaces-pane']/div/ul/li[12]/div[2]/a")).click();
		return new SCRMHomePage(driver);
	}

	public void selectHelpMenu() {
		//helpMenu.click();
		driver.findElement(By.id("help-menu-link")).click();
		driver.findElement(By.id("confluence-about-link")).click();
	}

	public boolean checkLicense() {
		return driver.getPageSource().contains("Confluence 5.5.3");
		
		/* --Old Code--
		return driver.findElement(By.xpath("//*[@id='about-confluence-dialog']/div/div[1]/div/h3[1]")).toString();
		assertTrue(driver.findElement(By.xpath("//*[@id='about-confluence-dialog']/div/div[1]/div/h3[1]")).getText().matches("Confluence 5.5.3"));
		**/
	}

	public void closeHelpMenu() {
		driver.findElement(By.xpath("//*[@id='about-confluence-dialog']/div/div[2]/a")).click();
	}
	
	public void logOut() {
		driver.findElement(By.id("user-menu-link")).click();
		driver.findElement(By.id("logout-link")).click();
	}

	public void selectAdminMenu() {
		driver.findElement(By.id("admin-menu-link")).click();
	}

	public void selectAddOns() {
		selectAdminMenu();
		driver.findElement(By.id("plugin-administration-link")).click();
	}

	public void enterPassword(String password) {
		//passwordTB.sendKeys(password);
		driver.findElement(By.name("password")).sendKeys(password);
	}

	public HomePage selectLoginButton() {
		driver.findElement(By.id("authenticateButton")).click();
		return new HomePage(driver);
	}
}
