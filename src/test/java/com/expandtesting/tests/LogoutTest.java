package com.expandtesting.tests;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.expandtesting.pages.LoginPage;
import com.expandtesting.pages.SecurePage;
import com.expandtesting.utils.ConfigLoader;
import com.fasterxml.jackson.databind.JsonNode;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LogoutTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private SecurePage securePage;
    private WebDriverWait wait;
    private JsonNode credentials;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().driverVersion("137.0.7151.55").setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage",
                "--remote-debugging-port=9222", "--disable-gpu",
                "--disable-setuid-sandbox", "--remote-allow-origins=*");
        options.setBinary("/usr/bin/google-chrome");
        if ("true".equalsIgnoreCase(System.getenv("HEADLESS"))) {
            options.addArguments("--headless");
        }
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        loginPage = new LoginPage(driver);
        securePage = new SecurePage(driver);

        // Load credentials from testdata.json
        JsonNode config = ConfigLoader.getConfig();
        credentials = config.get("credentials");
    }

    @Test
    public void testLogoutFunctionality() {
        driver.get("https://practice.expandtesting.com/login");

        // Ensure the username field is present
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='username']")));

        // Use JavaScript to set the value if standard sendKeys fails
        try {
            usernameField.sendKeys(credentials.get("username").asText());
        } catch (ElementNotInteractableException e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.getElementById('username').value='" + credentials.get("username").asText() + "';");
        }

        // Enter password and login
        loginPage.enterPassword(credentials.get("password").asText());
        loginPage.clickLogin();

        // Wait for the secure page to load before logout
        wait.until(ExpectedConditions.urlContains("/secure"));

        // Click the logout button
        securePage.clickLogout();

        // Wait for login page after logout
        wait.until(ExpectedConditions.urlContains("/login"));

        assertTrue(driver.getCurrentUrl().contains("/login"), "Logout did not return to the login page.");
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
