import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.*;

public class LanguageTest {
    private static WebDriver driver;

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
    void welcomePageInMultipleLanguages() {
        String temporaryPageSource = "";

        WebElement englishLink = driver.findElement(By.id("js-link-box-en"));
        englishLink.click();
        temporaryPageSource = driver.getPageSource();
        assertTrue(temporaryPageSource.contains("Welcome"));

        navigateToHomePage();

        WebElement germanLink = driver.findElement(By.id("js-link-box-de"));
        germanLink.click();
        temporaryPageSource = driver.getPageSource();
        assertTrue(temporaryPageSource.contains("Willkommen"));

        navigateToHomePage();

        WebElement italianLink = driver.findElement(By.id("js-link-box-it"));
        italianLink.click();
        temporaryPageSource = driver.getPageSource();
        assertTrue(temporaryPageSource.contains("Benvenuti"));
    }

    @Test
    public void changeAccountLanguage() throws InterruptedException {
        String loginUrl = "https://en.wikipedia.org/w/index.php?title=Special:UserLogin";
        String username = "Aldin-IBU-1";
        String password = "svvttest-1";

        driver.get(loginUrl);

        // Log in
        WebElement usernameField = driver.findElement(By.id("wpName1"));
        WebElement passwordField = driver.findElement(By.id("wpPassword1"));
        WebElement loginButton = driver.findElement(By.id("wpLoginAttempt"));
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();

        // Go To Settings -> Preferences
        WebElement accInput = driver.findElement(By.id("vector-user-links-dropdown-checkbox"));
        accInput.click();
        WebElement preferencesLink = driver.findElement(By.xpath(
                "//a[@href='/wiki/Special:Preferences']"));
        preferencesLink.click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,300)", "");
        Thread.sleep(600);

        WebElement languageDropdown = driver.findElement(By.cssSelector(
                "#mw-input-wplanguage > div:nth-child(2) > span:nth-child(1)"));
        languageDropdown.click();
        String desiredLanguage = "de - Deutsch";
        WebElement languageOption = driver.findElement(By.xpath(
                "//span[contains(@class,'oo-ui-labelElement-label') " +
                        "and text()='" + desiredLanguage + "']"));
        languageOption.click();

        js.executeScript("window.scrollBy(0,5000)", "");
        WebElement saveButton = driver.findElement(By.cssSelector("button.oo-ui-inputWidget-input"));
        saveButton.click();

        WebElement firstHeadingTag = driver.findElement(By.id("firstHeading"));
        String firstHeading = firstHeadingTag.getText();
        assertEquals("Einstellungen", firstHeading);
    }

    @Test
    public void testSearchForEnglishTerm() throws InterruptedException {
        String englishUrl = "https://en.wikipedia.org/";
        String nonEnglishUrl = "https://de.wikipedia.org/";
        String searchTerm = "Physics";

        driver.get(englishUrl);

        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.sendKeys(searchTerm);
        Thread.sleep(2000);

        WebElement searchButton = driver.findElement(By.xpath(
                "/html/body/div[1]/header/div[2]/div/div/div/form/div/button"));
        searchButton.click();

        // Verify that results are present
        WebElement firstHeadingTag = driver.findElement(By.id("firstHeading"));
        String firstHeading = firstHeadingTag.getText();
        assertTrue(firstHeading.contains(searchTerm));
        driver.get(nonEnglishUrl);

        // Locate the search input, enter the term, and submit
        searchBox = driver.findElement(By.name("search"));
        searchBox.sendKeys(searchTerm);
        searchButton = driver.findElement(By.id("searchButton"));
        Thread.sleep(2000);
        searchButton.click();

        // Verify that results are not present
        firstHeadingTag = driver.findElement(By.id("firstHeading"));
        firstHeading = firstHeadingTag.getText();
        assertFalse(firstHeading.contains(searchTerm));
    }

    @Test
    void testUnsupportedLanguageCode() {
        String unsupportedUrl = "https://xy.wikipedia.org/";

        try {
            // Attempt to navigate to the unsupported URL
            driver.get(unsupportedUrl);

            // If no exception, check the page source for an error message
            String pageSource = driver.getPageSource();
            assertTrue(pageSource.contains("We can’t connect to the server"),
                    "Expected a browser error message for an unsupported language URL.");
        } catch (org.openqa.selenium.WebDriverException e) {
            // Assert that the exception message contains the expected error
            assertTrue(e.getMessage().contains("dnsNotFound"),
                    "Expected DNS error for unsupported language URL.");
        }

    }}
