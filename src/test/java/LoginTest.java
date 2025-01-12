import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.*;


public class LoginTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
        driver = new FirefoxDriver();
    }

    @BeforeEach
    public void navigateToLoginPage() {
        driver.get("https://www.wikipedia.org/");
        WebElement englishLink = driver.findElement(By.id("js-link-box-en"));
        englishLink.click();

        WebElement loginLink = driver.findElement(By.xpath("/html/body/div[1]/header/div[2]/nav/div[1]/div[4]/div/ul/li[3]/a"));
        loginLink.click();
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void validLogin() throws InterruptedException {
        WebElement usernameField = driver.findElement(By.id("wpName1"));
        WebElement passwordField = driver.findElement(By.id("wpPassword1"));
        WebElement loginButton = driver.findElement(By.id("wpLoginAttempt"));

        String username = "Aldin-IBU-1";
        String password = "svvttest-1";

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        loginButton.click();

        Thread.sleep(5000);

        String currentUrl = driver.getCurrentUrl();

        // Valid Login Should Take Us To The Main Page
        String expectedUrl = "https://en.wikipedia.org/wiki/Main_Page";
        assertEquals(expectedUrl, currentUrl);
    }

    @Test
    public void invalidLogin() throws InterruptedException {
        WebElement usernameField = driver.findElement(By.id("wpName1"));
        WebElement passwordField = driver.findElement(By.id("wpPassword1"));
        WebElement loginButton = driver.findElement(By.id("wpLoginAttempt"));

        String username = "Aldin-IBU-1";
        String password = "wrongpassword";

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        loginButton.click();

        Thread.sleep(5000);

        String currentUrl = driver.getCurrentUrl();

        // Invalid Login Should Not Take Us To The Main Page
        String expectedUrl = "https://en.wikipedia.org/wiki/Main_Page";
        assertNotEquals(expectedUrl, currentUrl);

        // Invalid Login Will Produce One Of Error Messages Below
        String firstExpectedError = "Incorrect username or password entered.";
        String secondExpectedError = "There are problems with some of your input.";

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains(firstExpectedError) ||
                pageSource.contains(secondExpectedError));
    }
}
