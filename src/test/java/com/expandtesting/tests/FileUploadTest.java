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

import com.expandtesting.pages.FileUploadPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FileUploadTest {
    private WebDriver driver;
    private FileUploadPage fileUploadPage;
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
        driver.get("https://practice.expandtesting.com/upload");
        fileUploadPage = new FileUploadPage(driver);
    }

    @Test
    public void testFileUpload() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));
            System.out.println("File input is present.");
        } catch(Exception e) {
            System.out.println("File input not found. Page snippet:");
            System.out.println(driver.getPageSource().substring(0, 500));
            throw e;
        }

        String filePath = System.getProperty("user.dir") + "/test-upload/testfile.txt";
        fileUploadPage.uploadFile(filePath);

        // Wait for a confirmation element.
        try {
            // Update the XPath as needed; here, we look for elements containing 'Success' or 'Uploaded'
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Success') or contains(text(),'Uploaded')]")));
            System.out.println("Upload confirmation detected.");
        } catch(Exception e) {
            System.out.println("Upload confirmation element not visible. Page snippet:");
            System.out.println(driver.getPageSource().substring(0, 500));
        }
        assertTrue(fileUploadPage.isUploadSuccessful(), "File upload did not indicate success.");
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
