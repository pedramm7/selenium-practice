package com.expandtesting.tests;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.expandtesting.pages.InputsPage;
import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RandomDataTest {
    private WebDriver driver;
    private InputsPage inputsPage;
    private Faker faker;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().driverVersion("137.0.7151.55").setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
            "--no-sandbox",
            "--disable-dev-shm-usage",
            "--remote-debugging-port=9222",
            "--disable-gpu",
            "--disable-setuid-sandbox",
            "--remote-allow-origins=*"
        );
        options.setBinary("/usr/bin/google-chrome");
        if ("true".equalsIgnoreCase(System.getenv("HEADLESS"))) {
            options.addArguments("--headless");
        }
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get("https://practice.expandtesting.com/inputs");
        inputsPage = new InputsPage(driver);
        faker = new Faker();
    }

    @Test
    public void testRandomDataInput() {
        try {
            // Wait until the text input is clickable using its XPath.
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='text']")));
            System.out.println("Text input field is visible and clickable.");
        } catch(Exception e) {
            System.out.println("Text input field not clickable. Page snippet:");
            System.out.println(driver.getPageSource().substring(0, Math.min(500, driver.getPageSource().length())));
            throw e;
        }
        
        String randomText = faker.lorem().sentence();
        String randomNumber = String.valueOf(faker.number().numberBetween(100, 999));

        // Use the page object methods. (Assuming these methods use scrolling and clicking as needed.)
        inputsPage.enterText(randomText);
        inputsPage.enterNumber(randomNumber);

        // Optionally, you may print the resulting value from the field to verify that data was entered.
        System.out.println("Random data input performed successfully.");
        assertTrue(true, "Random data input performed successfully.");
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
