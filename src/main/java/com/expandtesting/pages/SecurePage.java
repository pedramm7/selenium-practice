package com.expandtesting.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SecurePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Updated logout button locator
    private final By logoutButton = By.xpath("//*[@id='core']/div/div/a");

    public SecurePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Increased wait time for stability
    }

    public void clickLogout() {
        WebElement logoutElem = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", logoutElem);
        logoutElem.click();
    }
}
