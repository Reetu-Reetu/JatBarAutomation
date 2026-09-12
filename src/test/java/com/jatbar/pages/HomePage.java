package com.jatbar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;

    private final String url =
            "https://jat-mobile-bar-website.vercel.app/";

    // Navigation locators
    private By homeLink =
            By.cssSelector("a[href='#home']");

    private By aboutLink =
            By.cssSelector("a[href='#about']");

    private By servicesLink =
            By.cssSelector("a[href='#services']");

    private By contactLink =
            By.cssSelector("a[href='#contact']");

    // Contact form
   private By contactForm = By.cssSelector("form.contact-form");


   public boolean isContactFieldDisplayed(String fieldId) {
    return driver.findElement(By.id(fieldId)).isDisplayed();
}
    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Open website
    public void open() {
        driver.get(url);
    }

    // Get page title
    public String getPageTitle() {
        return driver.getTitle();
    }

    // Click About
    public void clickAbout() {
        driver.findElement(aboutLink).click();
    }

    // Click Services
    public void clickServices() {
        driver.findElement(servicesLink).click();
    }

    // Click Contact
    public void clickContact() {
        driver.findElement(contactLink).click();
    }

    // Verify Contact form
    public boolean isContactFormDisplayed() {
        return driver.findElement(contactForm).isDisplayed();
    }

    // Verify navigation links are displayed
    public boolean isNavigationDisplayed() {

        return driver.findElement(homeLink).isDisplayed()
                && driver.findElement(aboutLink).isDisplayed()
                && driver.findElement(servicesLink).isDisplayed()
                && driver.findElement(contactLink).isDisplayed();
    }

    public void submitContactForm() {
    By submitButton = By.cssSelector(
            "form.contact-form button[type='submit']"
    );

    WebElement button = driver.findElement(submitButton);

    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block: 'center', behavior: 'instant'});",
            button
    );

    new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(submitButton))
            .click();
}

public String getContactFormText() {
    return driver.findElement(
            By.cssSelector("form.contact-form")
    ).getText();
}

public void enterContactEmail(String email) {
    driver.findElement(
            By.cssSelector("form.contact-form #email")
    ).sendKeys(email);
}

}