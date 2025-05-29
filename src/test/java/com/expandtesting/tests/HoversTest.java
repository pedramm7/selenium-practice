package com.expandtesting.tests;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.expandtesting.pages.HoversPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HoversTest {
    private WebDriver driver;
    private HoversPage hoversPage;

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
        hoversPage = new HoversPage(driver);
    }

    @Test
    public void testHoverAction() {
        driver.get("https://practice.expandtesting.com/hovers");

        hoversPage.hoverOverAvatar();
        assertTrue(hoversPage.isTooltipDisplayed(), "Tooltip was not displayed after hovering.");
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
