package pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	public WebDriver driver;
	//Actions action = new Actions(driver);
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	/*
	********************************************* 
	*	@FindBy(xpath="//*[@id='os_username']") *
	*	private WebElement userNameTB;          *
	*	                                        *
	*	@FindBy(xpath="//*[@id='os_password']") *
	*	private WebElement passwordTB;          *
	*	                                        *
	*	@FindBy(xpath="//*[@id='loginButton']") *
	*	private WebElement loginButton;         *
	*	*****************************************
	**/ 
	
	//@FindBy(xpath="//*[@id='recent-spaces-section']/ul/li[1]/a")
	//public WebElement spaceSCRM;
	
	
	public void enterUserName(String userName) {
		//userNameTB.sendKeys(userName);
		driver.findElement(By.id("os_username")).sendKeys(userName);
	}
	
	public void enterPassword(String password) {
		//passwordTB.sendKeys(password);
		driver.findElement(By.name("os_password")).sendKeys(password);
	}
	
	public HomePage selectLoginButton() {
		driver.findElement(By.id("loginButton")).click();
		return new HomePage(driver);
	}
	
	public TempPage login_TempPage() {
		driver.findElement(By.id("loginButton")).click();
		return new TempPage(driver);
	}
	
	public void createTab() {
		driver.findElement(By.id("create-page-button")).click();
		waitPageLoad();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	public BlankNewPage createBlankNewPage() {
		createButton();
		//driver.findElement(By.xpath("//*[@id='create-dialog']/div/div[1]/div/div[2]/div[2]/div[2]/ol/li[1]/div/div[1]")).click();
		driver.findElement(By.xpath("//*[@id='create-dialog']/div/div[2]/button")).click();
		return new BlankNewPage(driver);
	}
	
	// double click Action implemented
	public void createButton() {
		//action.moveToElement(driver.findElement(By.xpath("//*[@id='create-dialog']/div/div[1]/div/div[2]/div[2]/div[2]/ol/li[1]/div/div[1]"))).doubleClick().build().perform();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id='create-dialog']/div/div[2]/button")).click();
		
		//driver.findElement(By.xpath("//*[@id='create-dialog']/div/div[2]/a[2]")).click();
		//driver.findElement(By.className("button[normalize-space()='Create']")).click();
		
	}

	public void selectSCRMSpace() {
		driver.findElement(By.xpath("//*[@id='loginButton']")).click();
	}
	
	public void waitPageLoad() {
		
		WebElement waitElement = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='create-dialog']/div/div[2]/button")));
	}
	///???????
	public SpaceToolsPage addTemplateLink() {
		driver.findElement(By.id("create-page-button")).click();
		return new SpaceToolsPage(driver);
	}

	
}
