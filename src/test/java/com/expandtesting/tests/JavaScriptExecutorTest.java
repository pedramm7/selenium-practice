package com.expandtesting.tests;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class JavaScriptExecutorTest {
    private WebDriver driver;
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
    }

    @Test
    public void testJavaScriptExecution() {
        driver.get("https://practice.expandtesting.com/login");
        try {
            // Wait until the title contains the expected string
            wait.until(ExpectedConditions.titleContains("Test Login Page for Automation Testing Practice"));
            System.out.println("Login page loaded with expected title.");
        } catch(Exception e) {
            System.out.println("Login page title not as expected. Current title: " + driver.getTitle());
        }
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String title = (String) js.executeScript("return document.title;");
        System.out.println("JavaScript returned page title: " + title);
        
        // Update the expected title to match what is being returned
        assertEquals("Test Login Page for Automation Testing Practice", title, 
            "Expected page title to be 'Test Login Page for Automation Testing Practice'");
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
