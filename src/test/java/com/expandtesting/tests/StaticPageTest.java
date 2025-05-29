package com.expandtesting.tests;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.expandtesting.pages.BasePage;
import com.expandtesting.utils.ConfigLoader;
import com.fasterxml.jackson.databind.JsonNode;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StaticPageTest {
    private WebDriver driver;
    private BasePage basePage;
    private WebDriverWait wait;

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
        basePage = new BasePage(driver);
    }

    @Test
    public void testStaticPagesFromConfig() {
        JsonNode config = ConfigLoader.getConfig();
        JsonNode pages = config.get("staticPages");
        for (JsonNode page : pages) {
            String url = page.get("url").asText();
            String expectedTitle = page.get("title").asText();
            driver.get(url);

            // Wait for the page title to fully load
            try {
                wait.until(ExpectedConditions.titleContains(expectedTitle));
            } catch (Exception e) {
                System.out.println("Timed out waiting for expected title on URL: " + url);
            }

            String actualTitle = basePage.getPageTitle();
            System.out.println("URL: " + url + " | Expected title contains: " + expectedTitle + " | Actual title: " + actualTitle);
            assertTrue(actualTitle.contains(expectedTitle), "Title mismatch for " + url);
        }
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
