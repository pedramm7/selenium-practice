package com.expandtesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckboxesPage extends BasePage {
    private final By checkbox1 = By.id("checkbox1");
    private final By checkbox2 = By.id("checkbox2");

    public CheckboxesPage(WebDriver driver) {
        super(driver);
    }

    public void checkCheckbox1() {
        WebElement cb = driver.findElement(checkbox1);
        if (!cb.isSelected()) {
            cb.click();
        }
    }

    public void uncheckCheckbox1() {
        WebElement cb = driver.findElement(checkbox1);
        if (cb.isSelected()) {
            cb.click();
        }
    }

    public boolean isCheckbox1Checked() {
        return driver.findElement(checkbox1).isSelected();
    }

    public void checkCheckbox2() {
        WebElement cb = driver.findElement(checkbox2);
        if (!cb.isSelected()) {
            cb.click();
        }
    }

    public void uncheckCheckbox2() {
        WebElement cb = driver.findElement(checkbox2);
        if (cb.isSelected()) {
            cb.click();
        }
    }

    public boolean isCheckbox2Checked() {
        return driver.findElement(checkbox2).isSelected();
    }
}