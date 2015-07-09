package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempPage {

	public WebDriver driver;
	public final static String pageLink = "http://jenkins-stage.bscdev.bscal.com/confluence/display/SCRMP/Temp";
	
	public TempPage (WebDriver driver) {
		this.driver = driver;
	}

	public String openTempPage() {
		return pageLink;
	}
	
	public void editPage() {
		driver.findElement(By.id("editPageLink")).click();
		waitPageToLoad();
	}

	public void enterText() {
		driver.findElement(By.xpath("//*[@id='tinymce']")).sendKeys("Test: Edit Page");
	}

	public void saveButton() {
		driver.findElement(By.id("rte-button-publish")).click();
	}
	
	public TempPage cancelButton() {
		driver.findElement(By.id("rte-button-cancel")).click();
		return new TempPage(driver);
	}

	public void waitPageToLoad() {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		//WebElement waitElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tinymce")));
	}


}
