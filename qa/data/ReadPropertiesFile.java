package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ReadPropertiesFile {

	public WebDriver driver;
	protected Properties prop = null;
	protected File file = new File("C:/Users/vgjoze01/workspace/GridAutomation/qa/data/config.properties");
	protected FileInputStream fileInput = null;
	
	protected final String path = "C:/Users/vgjoze01/Downloads/chromedriver_win32/chromedriver.exe";
	
	public ReadPropertiesFile()  {
		prop = new Properties();
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public WebDriver getBrowser() {
		System.setProperty("webdriver.chrome.driver", path);
		return driver = new ChromeDriver();
		//return prop.getProperty("browser");
	}
		
	public String getUrl() {
		return prop.getProperty("url");
	}
	
	public String getUrlProd() {
		return prop.getProperty("urlProd");
	}
		
	public String getUserName() {
		return prop.getProperty("username");
	}
	
	public String getPassword() {
		return prop.getProperty("password");
	}

	public String getTestData() {
		return prop.getProperty("testData");
	}
	
	public String getSheetName() {
		return prop.getProperty("sheetEnv");
	}
	
	public String getChromeDriver() {
		return path;
	}
}
