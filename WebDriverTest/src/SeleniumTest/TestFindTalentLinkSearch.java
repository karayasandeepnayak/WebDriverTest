package SeleniumTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**************************************
 * Test Case - Test the working of Find Talent Link
 * Steps - Log into work market website, click 'Find Talent' link in the homepage
 *         Search for the keyword "test" and make sure that the keyword test is present in the each entry 
 *         of the search result.
 */
public class TestFindTalentLinkSearch {
	
	private static String baseURL = "https://dev.workmarket.com/login";
	private static WebDriver driver;
	public static WebElement findTalentLink;
	public final static String loginPageTitle = "Work Market | Login";
	public final static String homePageTitle = "Home - Work Market";
	public final static String SEARCH_KEYWORD = "test";
	public final static String[] credentials = {"qa+candidatetest@workmarket.com","candidate123"};
	
	public static void loginToWorkMarket() 
	{
		driver = new ChromeDriver();
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
		findTalentLink = wait.until(ExpectedConditions.presenceOfElementLocated(item));
				
		assertEquals(driver.getTitle(),homePageTitle);
	}

	public static void main(String[] args) {
	
		try {
			System.setProperty("webdriver.chrome.driver", "C:\\RobotFramework\\chromedriver.exe");
			
			//Log into the work market website
			loginToWorkMarket();
			
			//Click 'Find Talent' link
			findTalentLink.click();
		
			//Wait for the search to complete
			WebDriverWait clearButtonWait = new WebDriverWait(driver,10);
			clearButtonWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("clear_facets")));
				
			//Check if 'Clear This Search' button is visible.
			WebElement clearSearchButton = driver.findElement(By.id("clear_facets"));
			assertTrue(clearSearchButton.isDisplayed());
			assertTrue(clearSearchButton.isEnabled());
			clearSearchButton.click();
		
			//Search for Workers with the keyword "test"
			WebElement searchTextBox = driver.findElement(By.id("input-text"));
			searchTextBox.click();
			searchTextBox.sendKeys(SEARCH_KEYWORD);
			searchTextBox.sendKeys(Keys.RETURN);
		
			//Wait for the search to complete
			WebDriverWait wait = new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search_results")));
		
			//Count number of entries in the Search Results
			int numberOfWorkers = driver.findElements(By.xpath("//div[@class='profile-card']")).size();
	
			//Verify if the Searched Keyword appears in either Name or Skills attribute of each worker/search result
			for(int i=1; i<=numberOfWorkers; i++)
			{
				//Below used are the xpaths for name and skills section of each worker record
				String nameXpath = "//*[@id='search_results']/div["+i+"]/div[3]/h2/a";
				String skillsXPath = "//*[@id='search_results']/div["+i+"]/div[3]/ul[3]/div[2]";
		
				try
				{
					String name = driver.findElement(By.xpath(nameXpath)).getText().toLowerCase(); 
					if (!name.contains(SEARCH_KEYWORD))
					{
						String skills = driver.findElement(By.xpath(skillsXPath)).getText().toLowerCase();
						assertTrue(skills.contains(SEARCH_KEYWORD));
					}
				}
				catch (NoSuchElementException e)
				{
					//Do Nothing
					//This exception is thrown if skills of a worker is not listed. 
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
					System.exit(-1);
				}
			}
			
			//Logout
			WebElement logoutButton = driver.findElement(By.xpath("//*[@id='wm-main-nav']/div/div[2]/div[2]/div/div/div[4]/button"));
			logoutButton.click();
			
			//Close Browser
			driver.quit();
			System.out.println("TEST PASSED!!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			driver.quit();
			System.out.println("TEST FAILED!!");
		}
	}
	
}
