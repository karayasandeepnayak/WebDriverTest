package SeleniumTest;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * Test Case: Test Successful Login
 * Steps: Navigate to workmarket login page and enter correct credentials. 
 *        User should be able to login successfully and gain access to dashboard.
 */
public class TestSuccessfulLogin {

	private static String baseURL = "https://dev.workmarket.com/login";
	private static WebDriver driver;
	public final static String loginPageTitle = "Work Market | Login";
	public final static String homePageTitle = "Home - Work Market";
	public final static String[] credentials = {"qa+candidatetest@workmarket.com","candidate123"};
	
	public static void main(String[] args) {
		
		try 
		{
			System.setProperty("webdriver.chrome.driver", "C:\\RobotFramework\\chromedriver.exe");
			driver = new ChromeDriver();
			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
		
			//Enter Password
			WebElement passwordTextBox = driver.findElement(By.id("login-password"));
			assertEquals(passwordTextBox.isEnabled(),true);
			passwordTextBox.click();
			passwordTextBox.sendKeys(credentials[1]);

			//Click Login Button
			WebElement loginButton = driver.findElement(By.id("login_page_button"));
			assertEquals(loginButton.isEnabled(),true);
			loginButton.click();
		
			//Make sure dashboard is loaded and Find Talent link is enabled
			WebDriverWait wait = new WebDriverWait(driver, 40);
			By item = By.xpath("//*[@id='wm-main-nav']/div/div[1]/div/div/div[2]/div[2]/a/div/div/div");
			WebElement findTalentLink = wait.until(ExpectedConditions.presenceOfElementLocated(item));
		
			assertEquals(driver.getTitle(),homePageTitle);
		
			//Logout
			WebElement logoutButton = driver.findElement(By.xpath("//*[@id='wm-main-nav']/div/div[2]/div[2]/div/div/div[4]/button"));
			logoutButton.click();
		
			//Close the browser
			driver.quit();
			System.out.println("TEST PASSED!!");
		}
		catch (AssertionError e)
		{
			e.printStackTrace();
			
			//Logout
			WebElement logoutButton = driver.findElement(By.xpath("//*[@id='wm-main-nav']/div/div[2]/div[2]/div/div/div[4]/button"));
			logoutButton.click();
			
			//Close Browser
			driver.quit();
			System.out.println("TEST FAILED!!");
		}
	}

}
