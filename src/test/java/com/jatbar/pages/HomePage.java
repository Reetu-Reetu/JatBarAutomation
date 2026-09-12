package com.jatbar.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private final WebDriver driver;

    private static final String URL =
            "https://jat-mobile-bar-website.vercel.app/";

    private final By homeLink = By.cssSelector("a[href='#home']");
    private final By aboutLink = By.cssSelector("a[href='#about']");
    private final By servicesLink = By.cssSelector("a[href='#services']");
    private final By contactLink = By.cssSelector("a[href='#contact']");

    private final By contactForm =
            By.cssSelector("form.contact-form");
    private final By submitButton =
            By.cssSelector("form.contact-form button[type='submit']");
    private final By emailField =
            By.cssSelector("form.contact-form #email");
    private final By phoneField =
            By.cssSelector("form.contact-form #phone");
    private final By enquiryField =
            By.cssSelector("form.contact-form #query");
    private final By privacyCheckbox =
            By.cssSelector("form.contact-form #privacyConsent");
    private final By privacyError =
            By.id("privacyConsent-error");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(URL);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void clickAbout() {
        driver.findElement(aboutLink).click();
    }

    public void clickServices() {
        driver.findElement(servicesLink).click();
    }

    public void clickContact() {
    new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(contactLink))
            .click();
}

    public boolean isNavigationDisplayed() {
        return driver.findElement(homeLink).isDisplayed()
                && driver.findElement(aboutLink).isDisplayed()
                && driver.findElement(servicesLink).isDisplayed()
                && driver.findElement(contactLink).isDisplayed();
    }

    public boolean isContactFormDisplayed() {
        return driver.findElement(contactForm).isDisplayed();
    }

    public boolean isContactFieldDisplayed(String fieldId) {
        By field = By.cssSelector(
                "form.contact-form [id='" + fieldId + "']"
        );
        return driver.findElement(field).isDisplayed();
    }

    public void enterContactEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterContactPhone(String phone) {
        driver.findElement(phoneField).sendKeys(phone);
    }

    public void enterContactEnquiry(String enquiry) {
        driver.findElement(enquiryField).sendKeys(enquiry);
    }

    public String getContactEnquiryValue() {
        return driver.findElement(enquiryField).getAttribute("value");
    }

    public String getContactFormText() {
        return driver.findElement(contactForm).getText();
    }

    public void submitContactForm() {
        scrollToAndClick(submitButton);
    }

    public void agreeToPrivacyNotice() {
        scrollToAndClick(privacyCheckbox);
    }

    public boolean isPrivacyNoticeSelected() {
        return driver.findElement(privacyCheckbox).isSelected();
    }

    public boolean isPrivacyNoticeErrorDisplayed() {
        return driver.findElements(privacyError)
                .stream()
                .anyMatch(WebElement::isDisplayed);
    }

    private void scrollToAndClick(By locator) {
        WebElement element = driver.findElement(locator);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(" +
                "{block: 'center', behavior: 'instant'});",
                element
        );

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(locator))
                .click();
    }
}
