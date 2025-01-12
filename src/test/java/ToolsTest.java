import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToolsTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
        driver = new FirefoxDriver();
    }

    @BeforeEach
    public void navigateToHomePage() {
        driver.get("https://en.wikipedia.org/wiki/Software_testing");
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    void testShortenedUrl() throws InterruptedException {
        String regularUrlHeading = driver.findElement(By.id("firstHeading")).getText();
        WebElement toolsBar = driver.findElement(By.id("vector-page-tools-dropdown-checkbox"));
        toolsBar.click();

        WebElement urlShortener = driver.findElement(By.id("t-urlshortener"));
        urlShortener.click();

        Thread.sleep(2000);
        WebElement shortenedUrlInput = driver.findElement(By.id("ooui-2"));
        String shortenedUrl = shortenedUrlInput.getAttribute("value");

        Thread.sleep(2000);
        driver.get(shortenedUrl);

        String shortenedUrlHeading = driver.findElement(By.id("firstHeading")).getText();

        assertEquals(regularUrlHeading, shortenedUrlHeading);
    }

    @Test
    void testPermanentUrl() throws InterruptedException {
        String regularUrlHeading = driver.findElement(By.id("firstHeading")).getText();
        WebElement toolsBar = driver.findElement(By.id("vector-page-tools-dropdown-checkbox"));
        toolsBar.click();

        WebElement urlShortener = driver.findElement(By.id("t-permalink"));
        urlShortener.click();

        Thread.sleep(2000);

        String shortenedUrlHeading = driver.findElement(By.id("firstHeading")).getText();
        assertEquals(regularUrlHeading, shortenedUrlHeading);
    }

    @Test
    void testPageInformation() {
        WebElement toolsBar = driver.findElement(By.id("vector-page-tools-dropdown-checkbox"));
        toolsBar.click();

        WebElement pageInformation = driver.findElement(By.id("t-info"));
        pageInformation.click();

        String displayTitle = driver.findElement(By.cssSelector(
                "#mw-pageinfo-display-title > td:nth-child(2)")).getText();
        assertEquals("Software testing", displayTitle);
    }

}
