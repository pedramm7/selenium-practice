package com.expandtesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class DropdownPage {
    private final WebDriver driver;

    // XPath locator for dropdown element.
    private final By dropdown = By.xpath("//select[@id='dropdown' or contains(@class, 'dropdown')]");

    public DropdownPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectOption(String optionText) {
        Select select = new Select(driver.findElement(dropdown));
        select.selectByVisibleText(optionText);
    }

    public String getSelectedOption() {
        Select select = new Select(driver.findElement(dropdown));
        return select.getFirstSelectedOption().getText();
    }
}
