package com.expandtesting.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FileUploadPage {
    private final WebDriver driver;

    // Locator for the file input element.
    private final By fileInput = By.xpath("//input[@type='file']");
    // Updated locator for the Upload button using the provided absolute XPath.
    private final By uploadButton = By.xpath("/html/body/main/div[3]/div[2]/div/div/div[1]/div/form/button");
    // Locator for a confirmation element that indicates a successful upload.
    private final By uploadSuccess = By.xpath("//*[contains(text(),'Success') or contains(text(),'Uploaded')]");

    public FileUploadPage(WebDriver driver) {
        this.driver = driver;
    }

    public void uploadFile(String filePath) {
        // Set the file path in the file input field.
        WebElement fileInputElement = driver.findElement(fileInput);
        fileInputElement.sendKeys(filePath);

        // Wait until the Upload button is clickable.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement uploadBtn = wait.until(ExpectedConditions.elementToBeClickable(uploadButton));

        // Scroll the button into view.
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", uploadBtn);
        
        // Click the Upload button using JavaScript.
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", uploadBtn);
    }

    public boolean isUploadSuccessful() {
        // Check for the presence of an element that confirms a successful upload.
        return !driver.findElements(uploadSuccess).isEmpty();
    }
}
