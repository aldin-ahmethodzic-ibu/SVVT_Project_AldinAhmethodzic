import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddEmailTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
        driver = new FirefoxDriver();
    }

    @BeforeEach
    public void navigateToHomePage() {
        driver.get("https://en.wikipedia.org/wiki/Special:Homepage");
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    void testValidMail() {
        WebElement usernameInput = driver.findElement(By.id("wpName1"));
        WebElement passwordInput = driver.findElement(By.id("wpPassword1"));

        usernameInput.sendKeys("Aldin-IBU-1");
        passwordInput.sendKeys("svvttest-1");

        WebElement loginBtn = driver.findElement(By.id("wpLoginAttempt"));
        loginBtn.click();

        WebElement addEmailLink = driver.findElement(By.cssSelector(".growthexperiments-homepage-startemail-noemail-link"));
        addEmailLink.click();

        WebElement newEmailAddressInput = driver.findElement(By.name("wpNewEmail"));
        newEmailAddressInput.sendKeys("aldin.ahmethodzic@stu.ibu.edu.ba");

        WebElement changeEmailBtn = driver.findElement(By.cssSelector("button.oo-ui-inputWidget-input"));
        changeEmailBtn.click();

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("A confirmation email has been sent"));
    }

    // Note: Fails Because It Allows Input Of Non-Existent Emails
    @Test
    void testInvalidMail() {
        WebElement usernameInput = driver.findElement(By.id("wpName1"));
        WebElement passwordInput = driver.findElement(By.id("wpPassword1"));

        usernameInput.sendKeys("Aldin-IBU-1");
        passwordInput.sendKeys("svvttest-1");

        WebElement loginBtn = driver.findElement(By.id("wpLoginAttempt"));
        loginBtn.click();

        WebElement addEmailLink = driver.findElement(By.cssSelector(
                ".growthexperiments-homepage-startemail-noemail-link"));
        addEmailLink.click();

        WebElement newEmailAddressInput = driver.findElement(By.name("wpNewEmail"));
        newEmailAddressInput.sendKeys("non.existent@email.com");

        WebElement changeEmailBtn = driver.findElement(By.cssSelector(
                "button.oo-ui-inputWidget-input"));
        changeEmailBtn.click();

        String pageSource = driver.getPageSource();
        assertFalse(pageSource.contains("A confirmation email has been sent"));
    }
}