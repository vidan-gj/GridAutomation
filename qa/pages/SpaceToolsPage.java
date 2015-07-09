package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SpaceToolsPage {

	public WebDriver driver;
	
	public SpaceToolsPage(WebDriver driver) {
		this.driver = driver;
	}
	
@FindBy(xpath = "//*[@id='content']/nav/div/div/ul/li[1]/a")
public WebElement overviewLink;





public void selectOverview() {
	overviewLink.click();
}
	
	
}
