package SeleniumTest;

import static org.junit.Assert.assertEquals;

import java.net.HttpURLConnection;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/************************************************************
 * Test Case: Work Market login page is not reachable
 * Steps: Open browser and navigate to work market login web page.
 *        To simulate Webpage not being reachable, I have provided INVALID URL.
 */
public class TestLoginPageNotReachable {
	
	private static String invalidBaseURL = "https://dev.workmarket1.com/login";
	private static WebDriver driver;
	public static final String errorMessage = "This site can’t be reached";
	
	public static void main(String[] args) {
		try 
		{
			System.setProperty("webdriver.chrome.driver", "C:\\RobotFramework\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(invalidBaseURL);
		
			//Retrieve message displayed on the web page and verify.
			WebElement message = driver.findElement(By.xpath("//*[@id='main-message']/h1"));		
			assertEquals(message.getText(), errorMessage);
			
			//Close Browser
			driver.quit();
			System.out.println("TEST PASSED!!");
		}
		catch (AssertionError e)
		{
			e.printStackTrace();
			
			//Close Browser
			driver.quit();
			System.out.println("TEST FAILED!!");
		}
	}
}
