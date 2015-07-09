package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.ClickAction;

public class SCRMHomePage {

	public WebDriver driver;
	public static final String pageURL = "http://jenkins-stage.bscdev.bscal.com/confluence/display/SCRM/SCRM+Home";
	
	public SCRMHomePage (WebDriver driver) {
		this.driver = driver;
	}
	
	public String getPageTitle() {
		return driver.getTitle();
	}
	
	public ContactListPage openContactListPage() {
		driver.findElement(By.xpath("//*[@id='content']/div[5]/div[2]/div/div/div[1]/ul[2]/li[5]/a")).click();
		return new ContactListPage(driver);
	}

}
