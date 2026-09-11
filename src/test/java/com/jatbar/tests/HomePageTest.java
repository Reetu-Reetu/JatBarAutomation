package com.jatbar.tests;

import com.jatbar.base.BaseTest;
import com.jatbar.pages.HomePage;

import java.time.Duration;

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
}