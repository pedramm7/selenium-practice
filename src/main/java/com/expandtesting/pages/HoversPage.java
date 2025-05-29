package com.expandtesting.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HoversPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By avatar = By.xpath("//*[contains(@class, 'figure')]");
    private final By tooltip = By.xpath("//*[contains(@class, 'figcaption')]");

    public HoversPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void hoverOverAvatar() {
        WebElement avatarElement = wait.until(ExpectedConditions.presenceOfElementLocated(avatar));

        Actions actions = new Actions(driver);
        actions.moveToElement(avatarElement).perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(tooltip));
    }

    public boolean isTooltipDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(tooltip)).isDisplayed();
        } catch (Exception e) {
            System.out.println("Tooltip did not appear within the expected time.");
            return false;
        }
    }
}
