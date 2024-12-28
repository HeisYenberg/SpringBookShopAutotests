package com.heisyenberg.tests;

import com.heisyenberg.pages.BooksPage;
import com.heisyenberg.pages.LoginPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.heisyenberg.data.BooksData.EMPTY_FIELD_ERROR;
import static com.heisyenberg.data.LoginData.ADMIN_EMAIL;
import static com.heisyenberg.data.LoginData.ADMIN_PASSWORD;
import static com.heisyenberg.data.LoginData.ADMIN_USERNAME;
import static com.heisyenberg.data.LoginData.INVALID_EMAIL_ADDRESS_FORMAT;
import static com.heisyenberg.data.LoginData.INVALID_EMAIL_OR_PASSWORD_ERROR;
import static com.heisyenberg.data.LoginData.USER_EMAIL;
import static com.heisyenberg.data.LoginData.USER_PASSWORD;
import static com.heisyenberg.data.LoginData.USER_USERNAME;

@Feature("Login page")
public class LoginTest extends BaseTest {
    private BooksPage booksPage;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        booksPage = open(BooksPage.PAGE_URL, BooksPage.class);
        loginPage = booksPage.clickLoginButton();
    }

    @Story("Check login as admin")
    @Test(description = "Check login as admin")
    public void checkLoginAsAdmin() {
        loginPage
                .setEmailInput(ADMIN_EMAIL)
                .setPasswordInput(ADMIN_PASSWORD)
                .clickSubmitLoginButton();
        booksPage
                .checkOpen()
                .checkAdminRoleHeader(ADMIN_USERNAME)
                .clickLogoutButton();
    }

    @Story("Check login as user")
    @Test(description = "Check login as user")
    public void checkLoginAsUser() {
        loginPage
                .setEmailInput(USER_EMAIL)
                .setPasswordInput(USER_PASSWORD)
                .clickSubmitLoginButton();
        booksPage
                .checkOpen()
                .checkUserRoleHeader(USER_USERNAME)
                .clickLogoutButton();
    }

    @Story("Check submitting empty email")
    @Test(description = "Check submitting empty email")
    public void checkSubmittingEmptyEmail() {
        loginPage
                .clickLoginButton()
                .checkEmailValidationMassage(EMPTY_FIELD_ERROR);
    }

    @Story("Check submitting invalid email")
    @Test(description = "Check submitting invalid email")
    public void checkSubmittingInvalidEmail() {
        loginPage
                .setEmailInput(USER_PASSWORD)
                .clickSubmitLoginButton()
                .checkEmailValidationMassage(String.format(INVALID_EMAIL_ADDRESS_FORMAT, USER_PASSWORD));
    }

    @Story("Check submitting empty password")
    @Test(description = "Check submitting empty password")
    public void checkSubmittingEmptyPassword() {
        loginPage
                .setEmailInput(ADMIN_EMAIL)
                .clickLoginButton()
                .checkPasswordValidationMessage(EMPTY_FIELD_ERROR);
    }

    @Story("Check submitting incorrect credentials")
    @Test(description = "Check submitting incorrect credentials")
    public void checkSubmittingIncorrectCredentials() {
        loginPage
                .setEmailInput(ADMIN_EMAIL)
                .setPasswordInput(USER_PASSWORD)
                .clickSubmitLoginButton()
                .checkAuthorizationErrorMessage(INVALID_EMAIL_OR_PASSWORD_ERROR);
    }
}
