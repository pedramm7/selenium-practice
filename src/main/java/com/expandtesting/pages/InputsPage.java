package com.expandtesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InputsPage {
    private final WebDriver driver;

    // Updated locators based on the HTML snippet.
    private final By numberInput = By.id("input-number");
    private final By textInput = By.id("input-text");
    private final By passwordInput = By.id("input-password");
    private final By dateInput = By.id("input-date");

    public InputsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterNumber(String number) {
        WebElement elem = driver.findElement(numberInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);
        try { Thread.sleep(500); } catch (InterruptedException e) { }
        elem.clear();
        elem.sendKeys(number);
    }

    public void enterText(String text) {
        WebElement elem = driver.findElement(textInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);
        try { Thread.sleep(500); } catch (InterruptedException e) { }
        elem.clear();
        elem.sendKeys(text);
    }

    public void enterPassword(String password) {
        WebElement elem = driver.findElement(passwordInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);
        try { Thread.sleep(500); } catch (InterruptedException e) { }
        elem.clear();
        elem.sendKeys(password);
    }

    public void enterDate(String date) {
        WebElement elem = driver.findElement(dateInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);
        try { Thread.sleep(500); } catch (InterruptedException e) { }
        // Set the date using JavaScript to avoid browser-specific reformatting issues.
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].value = arguments[1];", elem, date);
    }
}
