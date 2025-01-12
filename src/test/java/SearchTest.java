import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

public class SearchTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
        driver = new FirefoxDriver();
    }

    @BeforeEach
    public void navigateToHomePage() {
        driver.get("https://www.wikipedia.org/");
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void testSearchWithInvalidInput() {
        WebElement searchBox = driver.findElement(By.id("searchInput"));
        String searchInput = "a!2zf#=&%bct";
        searchBox.sendKeys(searchInput);

        WebElement submitButton = driver.findElement(By.xpath(
                "/html/body/main/div[2]/form/fieldset/button"));
        submitButton.click();

        String expectedOutput = "There were no results matching the query.";
        String pageOutput = driver.getPageSource();

        assertTrue(pageOutput.contains(expectedOutput));
    }

    @Test
    public void testSearchWithValidInput() {
        WebElement searchBox = driver.findElement(By.id("searchInput"));
        String searchInput = "World War I";
        searchBox.sendKeys(searchInput);

        WebElement submitButton = driver.findElement(By.xpath(
                "/html/body/main/div[2]/form/fieldset/button"));
        submitButton.click();

        String unexpectedOutput = "There were no results matching the query.";
        String pageOutput = driver.getPageSource();

        WebElement mainHeadingTag = driver.findElement(By.id("firstHeading"));
        String mainHeading = mainHeadingTag.getText();

        // Assert the main heading contains what we searched for
        assertEquals(searchInput, mainHeading);

        // Assert there is no message that there were no results found
        assertFalse(pageOutput.contains(unexpectedOutput));
    }
}
