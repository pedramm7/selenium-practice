package com.expandtesting.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Corrected locators based on provided XPath
    private final By usernameField = By.xpath("//*[@id='username']");
    private final By passwordField = By.xpath("//*[@id='password']");
    private final By loginButton = By.xpath("//*[@id='login']/button");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void enterUsername(String username) {
        WebElement userElem = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", userElem);
        wait.until(ExpectedConditions.elementToBeClickable(usernameField));
        userElem.clear();
        userElem.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement passElem = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", passElem);
        wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        passElem.clear();
        passElem.sendKeys(password);
    }

    public void clickLogin() {
        WebElement loginElem = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginElem);
        loginElem.click();
    }
}
