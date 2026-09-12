package com.jatbar.tests;

import java.time.Duration;

import com.jatbar.base.BaseTest;
import com.jatbar.pages.HomePage;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    private WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private HomePage openContactForm() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.clickContact();

        getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("form.contact-form")
        ));

        return homePage;
    }

    @Test
    public void verifyHomePageLoads() {
        HomePage homePage = new HomePage(driver);
        homePage.open();

        getWait().until(
                ExpectedConditions.titleIs("Jat Mobile Bar and Events")
        );

        Assert.assertEquals(
                homePage.getPageTitle(),
                "Jat Mobile Bar and Events",
                "Website title does not match"
        );
    }

    @Test
    public void verifyNavigationMenuDisplayed() {
        HomePage homePage = new HomePage(driver);
        homePage.open();

        boolean navigationDisplayed = getWait().until(
                webDriver -> homePage.isNavigationDisplayed()
        );

        Assert.assertTrue(
                navigationDisplayed,
                "One or more navigation links are not displayed"
        );
    }

    @Test
    public void verifyAboutNavigation() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.clickAbout();

        getWait().until(ExpectedConditions.urlContains("#about"));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("#about"),
                "URL does not contain #about. Actual URL: "
                        + driver.getCurrentUrl()
        );
    }

    @Test
    public void verifyServicesNavigation() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.clickServices();

        getWait().until(ExpectedConditions.urlContains("#services"));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("#services"),
                "URL does not contain #services. Actual URL: "
                        + driver.getCurrentUrl()
        );
    }

    @Test
    public void verifyContactNavigation() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.clickContact();

        getWait().until(ExpectedConditions.urlContains("#contact"));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("#contact"),
                "URL does not contain #contact. Actual URL: "
                        + driver.getCurrentUrl()
        );
    }

    @Test
    public void verifyHomePageUrl() {
        HomePage homePage = new HomePage(driver);
        homePage.open();

        String expectedUrl =
                "https://jat-mobile-bar-website.vercel.app/";

        getWait().until(ExpectedConditions.urlToBe(expectedUrl));

        Assert.assertEquals(
                driver.getCurrentUrl(),
                expectedUrl,
                "Homepage URL does not match"
        );
    }

    @Test
    public void verifyContactFormDisplayed() {
        HomePage homePage = openContactForm();

        Assert.assertTrue(
                homePage.isContactFormDisplayed(),
                "Contact form is not displayed after clicking Contact"
        );
    }

    @Test
    public void verifyContactFormFieldsDisplayed() {
        HomePage homePage = openContactForm();

        String[] fieldIds = {
                "firstName", "lastName", "email", "phone", "query"
        };

        for (String fieldId : fieldIds) {
            Assert.assertTrue(
                    homePage.isContactFieldDisplayed(fieldId),
                    "Contact form field is not displayed: " + fieldId
            );
        }
    }

    @Test
    public void verifyEmptyContactFormShowsValidationErrors() {
        HomePage homePage = openContactForm();
        homePage.submitContactForm();

        getWait().until(webDriver ->
                homePage.getContactFormText()
                        .contains("Email address is required.")
        );

        String formText = homePage.getContactFormText();

        Assert.assertTrue(
                formText.contains("Email address is required."),
                "Email required message is missing"
        );
        Assert.assertTrue(
                formText.contains("Telephone number is required."),
                "Telephone required message is missing"
        );
        Assert.assertTrue(
                formText.contains(
                        "Please briefly explain your requirements."
                ),
                "Enquiry required message is missing"
        );
        Assert.assertTrue(
                formText.contains(
                        "You must agree to the privacy notice."
                ),
                "Privacy notice required message is missing"
        );
    }

    @Test
    public void verifyInvalidEmailShowsValidationError() {
        HomePage homePage = openContactForm();
        homePage.enterContactEmail("not-an-email");
        homePage.submitContactForm();

        String expectedError = "Enter a valid email address.";

        getWait().until(webDriver ->
                homePage.getContactFormText().contains(expectedError)
        );

        Assert.assertTrue(
                homePage.getContactFormText().contains(expectedError),
                "Invalid email validation message is missing"
        );
    }

    @Test
    public void verifyShortEnquiryShowsValidationError() {
        HomePage homePage = openContactForm();
        homePage.enterContactEnquiry("123456789");
        homePage.submitContactForm();

        String expectedError =
                "Your enquiry must contain at least 10 characters.";

        getWait().until(webDriver ->
                homePage.getContactFormText().contains(expectedError)
        );

        Assert.assertTrue(
                homePage.getContactFormText().contains(expectedError),
                "Minimum enquiry length error is missing"
        );
    }

    @Test
    public void verifyTenCharacterEnquiryPassesLengthValidation() {
        HomePage homePage = openContactForm();
        homePage.enterContactEnquiry("1234567890");
        homePage.submitContactForm();

        getWait().until(webDriver ->
                homePage.getContactFormText()
                        .contains("Email address is required.")
        );

        Assert.assertFalse(
                homePage.getContactFormText().contains(
                        "Your enquiry must contain at least 10 characters."
                ),
                "A valid 10-character enquiry was rejected as too short"
        );
    }

    @Test
    public void verifyEnquiryStopsAtOneThousandCharacters() {
        HomePage homePage = openContactForm();
        homePage.enterContactEnquiry("a".repeat(1001));

        Assert.assertEquals(
                homePage.getContactEnquiryValue().length(),
                1000,
                "Enquiry field should accept no more than 1,000 characters"
        );
    }

    @Test
    public void verifyInvalidPhoneShowsValidationError() {
        HomePage homePage = openContactForm();
        homePage.enterContactPhone("abcd");
        homePage.submitContactForm();

        String expectedError = "Enter a valid telephone number.";

        getWait().until(webDriver ->
                homePage.getContactFormText().contains(expectedError)
        );

        Assert.assertTrue(
                homePage.getContactFormText().contains(expectedError),
                "Invalid telephone number error is missing"
        );
    }

    @Test
    public void verifyPrivacyConsentClearsItsValidationError() {
        HomePage homePage = openContactForm();
        homePage.agreeToPrivacyNotice();

        Assert.assertTrue(
                homePage.isPrivacyNoticeSelected(),
                "Privacy checkbox was not selected"
        );

        // Required fields remain empty, so no enquiry is sent.
        homePage.submitContactForm();

        getWait().until(webDriver ->
                homePage.getContactFormText()
                        .contains("Email address is required.")
        );

        Assert.assertFalse(
                homePage.isPrivacyNoticeErrorDisplayed(),
                "Privacy notice error appeared despite consent being selected"
        );
    }
}