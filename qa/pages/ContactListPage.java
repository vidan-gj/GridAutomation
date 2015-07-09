package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.ClickAction;
//import com.atlassian.confluence.selenium.client;

public class ContactListPage {

	public WebDriver driver;
	public static final String pageURL = "https://grid.bsc.bscal.com/display/SCRM/SCRM+Contact+List";
	
	public ContactListPage (WebDriver driver) {
		this.driver = driver;
	}
	
	public String getPageTitle() {
		return driver.getTitle();
	}
	
	public Boolean editPageCheck() {
		if (driver.findElement(By.id("editPageLink")).isDisplayed()) {
			System.out.println("ERROR: Anonymous User can Edit Page. ");
		
		} return null;
	}

}
