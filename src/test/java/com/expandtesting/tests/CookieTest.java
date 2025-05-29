package com.expandtesting.tests;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CookieTest {
    private WebDriver driver;

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
        if("true".equalsIgnoreCase(System.getenv("HEADLESS"))) {
          options.addArguments("--headless");
        }
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://practice.expandtesting.com/");
    }

    @Test
    public void testCookieInjection() {
        driver.manage().deleteAllCookies();
        driver.manage().addCookie(new Cookie("consent", "true"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) { }
        assertNotNull(driver.manage().getCookieNamed("consent"), "Cookie 'consent' was not added.");
    }

    @AfterEach
    public void teardown() {
        if(driver != null) {
            driver.quit();
        }
    }
}
