import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SandboxTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
        driver = new FirefoxDriver();
    }

    @BeforeEach
    public void navigateToHomePage() {
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
    void testCreateSandbox () {
        WebElement usernameInput = driver.findElement(By.id("wpName1"));
        WebElement passwordInput = driver.findElement(By.id("wpPassword1"));

        usernameInput.sendKeys("Aldin-IBU-2");
        passwordInput.sendKeys("svvttest-2");

        WebElement loginBtn = driver.findElement(By.id("wpLoginAttempt"));
        loginBtn.click();

        WebElement moreOptions = driver.findElement(By.id(
                "vector-user-links-dropdown-checkbox"));
        moreOptions.click();

        WebElement sandboxOption = driver.findElement(By.id("pt-sandbox"));
        sandboxOption.click();

        WebElement textboxArea = driver.findElement(By.id("wpTextbox1"));
        textboxArea.sendKeys("\nTest Input");

        WebElement publishBtn = driver.findElement(By.id("wpSave"));
        publishBtn.click();

        assertTrue(driver.getPageSource().contains("Test Input"));
    }

    @Test
    void testEditSandbox () {
        WebElement usernameInput = driver.findElement(By.id("wpName1"));
        WebElement passwordInput = driver.findElement(By.id("wpPassword1"));

        usernameInput.sendKeys("Aldin-IBU-2");
        passwordInput.sendKeys("svvttest-2");

        WebElement loginBtn = driver.findElement(By.id("wpLoginAttempt"));
        loginBtn.click();

        WebElement moreOptions = driver.findElement(By.id(
                "vector-user-links-dropdown-checkbox"));
        moreOptions.click();

        WebElement sandboxOption = driver.findElement(By.id("pt-sandbox"));
        sandboxOption.click();

        WebElement editSourceLink = driver.findElement(By.cssSelector(
                "#ca-edit > a:nth-child(1)"));
        editSourceLink.click();

        WebElement textboxArea = driver.findElement(By.id("wpTextbox1"));
        textboxArea.sendKeys("\nTest Input Edited");

        WebElement publishBtn = driver.findElement(By.id("wpSave"));
        publishBtn.click();

        assertTrue(driver.getPageSource().contains("Test Input Edited"));
    }

}
