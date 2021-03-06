package SeleniumTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestLoginWithBlankPassword {

	private static String baseURL = "https://dev.workmarket.com/login";
	private static WebDriver driver;
	public final static String loginPageTitle = "Work Market | Login";
	public final static String loginErrorMessage = "invalid user name or password";
	public final static String[] credentials = {"qa+candidatetest@workmarket.com","password123"};
	
	public static void main(String[] args) {
		
		try
		{
			System.setProperty("webdriver.chrome.driver", "C:\\RobotFramework\\chromedriver.exe");
			driver = new ChromeDriver();
			//Navigage to workmarket login page
			driver.get(baseURL);
		
			//Maximize browser and verify login page title
			driver.manage().window().maximize();
			assertEquals(driver.getTitle(), loginPageTitle);
		
			//Enter username 
			WebElement usernameTextBox = driver.findElement(By.id("login-email"));
			assertEquals(usernameTextBox.isEnabled(),true);
			usernameTextBox.click();
			usernameTextBox.sendKeys(credentials[0]);
		
			//Click Login Button
			WebElement loginButton = driver.findElement(By.id("login_page_button"));
			assertEquals(loginButton.isEnabled(),true);
			loginButton.click();
				
			//Verify if error message is displayed
			WebElement loginMessage = driver.findElement(By.id("login-messages"));
			assertTrue(loginMessage.isDisplayed());
			String message = loginMessage.getText().toLowerCase();
			assertTrue(message.contains(loginErrorMessage));
			
			//Close Browser
			driver.quit();
			System.out.println("TEST PASSED!!");
		}
		catch (AssertionError e)
		{
			e.printStackTrace();
		
			//Close Browser
			//driver.quit();
			System.out.println("TEST FAILED!!");
		}
	}
}
