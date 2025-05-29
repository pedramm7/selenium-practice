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

import com.expandtesting.pages.DropdownPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DropdownTest {
    private WebDriver driver;
    private DropdownPage dropdownPage;
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

        driver.get("https://practice.expandtesting.com/dropdown");
        dropdownPage = new DropdownPage(driver);
    }

    @Test
    public void testDropdownSelection() {
        // Wait until the dropdown element is clickable using XPath.
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='dropdown' or contains(@class, 'dropdown')]")));
            System.out.println("Dropdown element is clickable.");
        } catch(Exception e) {
            System.out.println("Dropdown element not clickable. Page snippet:");
            System.out.println(driver.getPageSource().substring(0, Math.min(500, driver.getPageSource().length())));
            throw e;
        }
        dropdownPage.selectOption("Option 2");
        assertEquals("Option 2", dropdownPage.getSelectedOption(), "Dropdown did not select the expected option.");
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
