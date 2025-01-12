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

public class DonationTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
        driver = new FirefoxDriver();
    }

    @BeforeEach
    public void navigateToHomePage() {
        driver.get("https://donate.wikimedia.org/");
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    void invalidInput() throws InterruptedException {
        Thread.sleep(2000);
        WebElement customInput = driver.findElement(By.id("input_amount_other_box"));
        customInput.sendKeys("0.50");

        WebElement payButton = driver.findElement(By.cssSelector(
                "div.payment-method-div:nth-child(6) > button:nth-child(1)"));
        payButton.click();

        Thread.sleep(2000);

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Please select an amount"));
    }

    // Note: Credit Card Option Fails
    @Test
    void validInputCreditCard() throws InterruptedException {
        Thread.sleep(2000);
        WebElement customInput = driver.findElement(By.id("input_amount_other_box"));
        customInput.sendKeys("3.00");

        WebElement payButton = driver.findElement(By.cssSelector(
                "div.payment-method-div:nth-child(6) > button:nth-child(1)"));
        payButton.click();

        String pageSource = driver.getPageSource();
        System.out.println(pageSource);
        assertFalse(pageSource.contains("error"));
    }

    @Test
    void validOneTimeDonationPaypal() throws InterruptedException {
        Thread.sleep(2000);
        WebElement customInput = driver.findElement(By.id("input_amount_other_box"));
        customInput.sendKeys("3.00");

        WebElement payButton = driver.findElement(By.cssSelector(
                "div.payment-method-div:nth-child(7) > button:nth-child(1)"));
        payButton.click();

        WebElement confirmPayment = driver.findElement(By.cssSelector("button.mc-button:nth-child(4)"));
        confirmPayment.click();

        Thread.sleep(10000);

        String pageUrl = driver.getCurrentUrl();
        assertTrue(pageUrl.contains("paypal.com"));
    }

    @Test
    void validMonthlyDonationPaypal() throws InterruptedException {
        Thread.sleep(2000);
        WebElement customInput = driver.findElement(By.id("input_amount_other_box"));
        customInput.sendKeys("3.00");

        WebElement payButton = driver.findElement(By.cssSelector(
                "div.payment-method-div:nth-child(7) > button:nth-child(1)"));
        payButton.click();

        WebElement confirmPayment = driver.findElement(By.cssSelector(".mc-yes-button"));
        confirmPayment.click();

        Thread.sleep(10000);

        String pageUrl = driver.getCurrentUrl();
        assertTrue(pageUrl.contains("paypal.com"));
    }

}
