package com.expandtesting.tests;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.expandtesting.pages.InputsPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class InputsTest {
    private WebDriver driver;
    private InputsPage inputsPage;
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get("https://practice.expandtesting.com/inputs");
        inputsPage = new InputsPage(driver);
    }

    @Test
    public void testFillInputs() {
        // Wait until the text input is visible.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-text")));

        // Fill inputs.
        inputsPage.enterNumber("123");
        inputsPage.enterText("Sample text");
        inputsPage.enterPassword("Secret123!");
        inputsPage.enterDate("2025-05-28");

        // Verify the values.
        String numberValue = driver.findElement(By.id("input-number")).getAttribute("value");
        String textValue = driver.findElement(By.id("input-text")).getAttribute("value");
        String passwordValue = driver.findElement(By.id("input-password")).getAttribute("value");
        String dateValue = driver.findElement(By.id("input-date")).getAttribute("value");

        assertEquals("123", numberValue, "Number input value did not match.");
        assertEquals("Sample text", textValue, "Text input value did not match.");
        assertEquals("Secret123!", passwordValue, "Password input value did not match.");
        assertEquals("2025-05-28", dateValue, "Date input value did not match.");
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
