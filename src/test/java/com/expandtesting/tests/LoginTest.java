package com.expandtesting.tests;

import com.expandtesting.pages.LoginPage;
import com.expandtesting.utils.ConfigLoader;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;
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

        // Load credentials from testdata.json
        JsonNode config = ConfigLoader.getConfig();
        credentials = config.get("credentials");
    }

    @Test
    public void testLogin() {
        driver.get("https://practice.expandtesting.com/login");

        // Ensure the username field is visible and interactable before entering text
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='username']")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='username']")));

        // Enter correct credentials from config file
        String username = credentials.get("username").asText();
        String password = credentials.get("password").asText();

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        // Wait until the secure area loads
        wait.until(ExpectedConditions.urlContains("/secure"));
        assertTrue(driver.getCurrentUrl().contains("/secure"), "Login did not navigate to the secure area.");
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
