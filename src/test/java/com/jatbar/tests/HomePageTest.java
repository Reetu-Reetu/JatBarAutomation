package com.jatbar.tests;

import com.jatbar.base.BaseTest;
import com.jatbar.pages.HomePage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    private WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void verifyHomePageLoads() {

        HomePage homePage = new HomePage(driver);
        homePage.open();

        boolean correctTitleLoaded = getWait().until(
                ExpectedConditions.titleIs("Jat Mobile Bar and Events")
        );

        Assert.assertTrue(
                correctTitleLoaded,
                "Website title does not match. Actual title: "
                        + homePage.getPageTitle()
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

        boolean aboutUrlLoaded = getWait().until(
                ExpectedConditions.urlContains("#about")
        );

        Assert.assertTrue(
                aboutUrlLoaded,
                "URL does not contain #about. Actual URL: "
                        + driver.getCurrentUrl()
        );
    }

    @Test
    public void verifyServicesNavigation() {

        HomePage homePage = new HomePage(driver);
        homePage.open();

        homePage.clickServices();

        boolean servicesUrlLoaded = getWait().until(
                ExpectedConditions.urlContains("#services")
        );

        Assert.assertTrue(
                servicesUrlLoaded,
                "URL does not contain #services. Actual URL: "
                        + driver.getCurrentUrl()
        );
    }

    @Test
    public void verifyContactNavigation() {

        HomePage homePage = new HomePage(driver);
        homePage.open();

        homePage.clickContact();

        boolean contactUrlLoaded = getWait().until(
                ExpectedConditions.urlContains("#contact")
        );

        Assert.assertTrue(
                contactUrlLoaded,
                "URL does not contain #contact. Actual URL: "
                        + driver.getCurrentUrl()
        );
    }


    @Test
    public void verifyHomeNavigation() {

        HomePage homePage = new HomePage(driver);
        homePage.open();

        String expectedUrl =
                "https://jat-mobile-bar-website.vercel.app/";

        boolean correctUrlLoaded = getWait().until(
                ExpectedConditions.urlToBe(expectedUrl)
        );

        Assert.assertTrue(
                correctUrlLoaded,
                "Homepage URL does not match. Actual URL: "
                        + driver.getCurrentUrl()
        );
    }

    @Test
public void verifyContactFormDisplayed() {

    HomePage homePage = new HomePage(driver);
    homePage.open();
    homePage.clickContact();

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    wait.until(ExpectedConditions.urlContains("#contact"));

    boolean formDisplayed = wait.until(
            webDriver -> homePage.isContactFormDisplayed()
    );

    Assert.assertTrue(
            formDisplayed,
            "Contact form is not displayed after clicking Contact"
    );
}

@Test
public void verifyContactFormFieldsDisplayed() {

    HomePage homePage = new HomePage(driver);
    homePage.open();
    homePage.clickContact();

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("form.contact-form")
    ));

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

    HomePage homePage = new HomePage(driver);
    homePage.open();
    homePage.clickContact();

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("form.contact-form")
    ));

    homePage.submitContactForm();

    wait.until(webDriver ->
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
            formText.contains("Please briefly explain your requirements."),
            "Enquiry required message is missing"
    );

    Assert.assertTrue(
            formText.contains("You must agree to the privacy notice."),
            "Privacy notice required message is missing"
    );
}


@Test
public void verifyInvalidEmailShowsValidationError() {

    HomePage homePage = new HomePage(driver);
    homePage.open();
    homePage.clickContact();

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("form.contact-form #email")
    ));

    homePage.enterContactEmail("not-an-email");
    homePage.submitContactForm();

    wait.until(webDriver ->
            homePage.getContactFormText()
                    .contains("Enter a valid email address.")
    );

    Assert.assertTrue(
            homePage.getContactFormText()
                    .contains("Enter a valid email address."),
            "Invalid email validation message is missing"
    );
}

@Test
public void verifyShortEnquiryShowsValidationError() {

    HomePage homePage = new HomePage(driver);
    homePage.open();
    homePage.clickContact();

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("form.contact-form #query")
    ));

    homePage.enterContactEnquiry("123456789"); // 9 characters
    homePage.submitContactForm();

    String expectedError =
            "Your enquiry must contain at least 10 characters.";

    wait.until(webDriver ->
            homePage.getContactFormText().contains(expectedError)
    );

    Assert.assertTrue(
            homePage.getContactFormText().contains(expectedError),
            "Minimum enquiry length error is missing"
    );
}
@Test
public void verifyTenCharacterEnquiryPassesLengthValidation() {

    HomePage homePage = new HomePage(driver);
    homePage.open();
    homePage.clickContact();

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("form.contact-form #query")
    ));

    homePage.enterContactEnquiry("1234567890"); // Exactly 10 characters
    homePage.submitContactForm();

    // Confirms validation has run while the form remains unsubmitted.
    wait.until(webDriver ->
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

    HomePage homePage = new HomePage(driver);
    homePage.open();
    homePage.clickContact();

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("form.contact-form #query")
    ));

    String enquiry = "a".repeat(1001);
    homePage.enterContactEnquiry(enquiry);

    Assert.assertEquals(
            homePage.getContactEnquiryValue().length(),
            1000,
            "Enquiry field should accept no more than 1,000 characters"
    );
}

@Test
public void verifyInvalidPhoneShowsValidationError() {

    HomePage homePage = new HomePage(driver);
    homePage.open();
    homePage.clickContact();

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("form.contact-form #phone")
    ));

    homePage.enterContactPhone("abcd");
    homePage.submitContactForm();

    String expectedError = "Enter a valid telephone number.";

    wait.until(webDriver ->
            homePage.getContactFormText().contains(expectedError)
    );

    Assert.assertTrue(
            homePage.getContactFormText().contains(expectedError),
            "Invalid telephone number error is missing"
    );
}

@Test
public void verifyPrivacyConsentClearsItsValidationError() {

    HomePage homePage = new HomePage(driver);
    homePage.open();
    homePage.clickContact();

    homePage.agreeToPrivacyNotice();

    Assert.assertTrue(
            homePage.isPrivacyNoticeSelected(),
            "Privacy checkbox was not selected"
    );

    homePage.submitContactForm();

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    // Other fields are empty, so validation runs without sending an enquiry.
    wait.until(webDriver ->
            homePage.getContactFormText()
                    .contains("Email address is required.")
    );

    Assert.assertFalse(
            homePage.isPrivacyNoticeErrorDisplayed(),
            "Privacy notice error appeared despite consent being selected"
    );
}
}