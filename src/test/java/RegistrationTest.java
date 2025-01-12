import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
        driver = new FirefoxDriver();
    }

    @BeforeEach
    public void navigateToRegisterPage() {
        driver.get("https://www.wikipedia.org/");
        WebElement englishLink = driver.findElement(By.id("js-link-box-en"));
        englishLink.click();

        WebElement registrationLink = driver.findElement(By.xpath("/html/body/div[1]/header/div[2]/nav/div[1]/div[4]/div/ul/li[2]/a"));
        registrationLink.click();
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void validRegistration() throws InterruptedException {
        WebElement usernameField = driver.findElement(By.id("wpName2"));
        WebElement passwordField = driver.findElement(By.id("wpPassword2"));
        WebElement confirmPasswordField = driver.findElement(By.id("wpRetype"));
        // WebElement emailField = driver.findElement(By.id("wpEmail")); // Optional
        WebElement submitButton = driver.findElement(By.id("wpCreateaccount"));

        String username = "Aldin-IBU-3";
        String password = "svvttest-3";

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        confirmPasswordField.sendKeys(password);
        // emailField.sendKeys("example@mail.com"); // Optional
        Thread.sleep(10000); // Allow The Time To Solve Captcha

        submitButton.click();

        Thread.sleep(5000);

        String expectedOutput = "Welcome, " + username;
        String page = driver.getPageSource();
        assertTrue(page.contains(expectedOutput));
    }

    @Test
    public void nameAlreadyInUse() throws InterruptedException {
        WebElement usernameField = driver.findElement(By.id("wpName2"));
        String existingUsername = "Aldin";
        usernameField.sendKeys(existingUsername);

        Thread.sleep(2000); // Allow The Time For Error To Appear

        String page = driver.getPageSource();
        String expectedError = "Username entered already in use.";
        assertTrue(page.contains(expectedError));
    }

    @Test
    public void passwordTooShort() throws InterruptedException {
        WebElement usernameField = driver.findElement(By.id("wpName2"));
        WebElement passwordField = driver.findElement(By.id("wpPassword2"));
        WebElement confirmPasswordField = driver.findElement(By.id("wpRetype"));

        String shortPassword = "shortpw";
        String uniqueUsername = "Aldin-IBU-Unique";

        usernameField.sendKeys(uniqueUsername);
        passwordField.sendKeys(shortPassword);
        confirmPasswordField.sendKeys(shortPassword);

        Thread.sleep(2000); // Allow The Time For Error To Appear

        String page = driver.getPageSource();
        String expectedError = "Passwords must be at least 8 characters.";
        assertTrue(page.contains(expectedError));
    }

    @Test
    public void invalidUsername() throws InterruptedException {
        WebElement usernameField = driver.findElement(By.id("wpName2"));
        String existingUsername = "###";
        usernameField.sendKeys(existingUsername);

        Thread.sleep(2000); // Allow The Time For Error To Appear

        String page = driver.getPageSource();
        String expectedError = "You have not specified a valid username.";
        assertTrue(page.contains(expectedError));
    }

    @Test
    public void unmatchedPasswords() throws InterruptedException {
        WebElement usernameField = driver.findElement(By.id("wpName2"));
        WebElement passwordField = driver.findElement(By.id("wpPassword2"));
        WebElement confirmPasswordField = driver.findElement(By.id("wpRetype"));
        // WebElement emailField = driver.findElement(By.id("wpEmail")); // Optional
        WebElement submitButton = driver.findElement(By.id("wpCreateaccount"));

        String username = "Aldin-IBU-Unique";
        String password = "svvttest";
        String confirmPassword = "testsvvt";

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        confirmPasswordField.sendKeys(confirmPassword);
        // emailField.sendKeys("example@mail.com"); // Optional
        Thread.sleep(10000); // Allow The Time To Solve Captcha

        submitButton.click();

        Thread.sleep(5000);

        String expectedError = "The passwords you entered do not match.";
        String page = driver.getPageSource();
        assertTrue(page.contains(expectedError));
    }

    @Test
    public void passwordTooSimple() throws InterruptedException {
        WebElement usernameField = driver.findElement(By.id("wpName2"));
        WebElement passwordField = driver.findElement(By.id("wpPassword2"));
        WebElement confirmPasswordField = driver.findElement(By.id("wpRetype"));

        String shortPassword = "simple";
        String uniqueUsername = "Aldin-IBU-Unique";

        usernameField.sendKeys(uniqueUsername);
        passwordField.sendKeys(shortPassword);
        confirmPasswordField.sendKeys(shortPassword);

        Thread.sleep(2000); // Allow The Time For Error To Appear

        String page = driver.getPageSource();
        String expectedError = "The password entered is in a list of very commonly " +
                "used passwords. Please choose a more unique password.";
        assertTrue(page.contains(expectedError));
    }

}