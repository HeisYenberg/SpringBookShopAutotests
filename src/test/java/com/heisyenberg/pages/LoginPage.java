package com.heisyenberg.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.be;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.have;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static com.heisyenberg.utils.ElementsUtil.checkValidationMessage;
import static com.heisyenberg.utils.ElementsUtil.waitAndClick;

public class LoginPage extends BasePage {
    public static final String PAGE_URL = "/login";

    private final SelenideElement emailInput = $("#email");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $(".btn-primary[type='submit']");
    private final SelenideElement registrationLink = $("a[href='/registration']");
    private final SelenideElement errorMessage = $("div ~ div");

    @Step("Check login page open")
    public LoginPage checkOpen() {
        webdriver().shouldHave(urlContaining(PAGE_URL));
        checkUnauthorizedHeader();
        emailInput.shouldBe(visible);
        passwordInput.shouldBe(visible);
        loginButton.shouldBe(visible);
        registrationLink.shouldBe(visible);
        return this;
    }

    @Step("Set email input: {email}")
    public LoginPage setEmailInput(final String email) {
        emailInput.setValue(email);
        return this;
    }

    @Step("Set password input: {password}")
    public LoginPage setPasswordInput(final String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Click submit login button")
    public LoginPage clickSubmitLoginButton() {
        waitAndClick(loginButton);
        return this;
    }

    @Step("Authorize")
    public BooksPage authorize(final String email, final String password) {
        setEmailInput(email);
        setPasswordInput(password);
        clickSubmitLoginButton();
        return new BooksPage();
    }

    @Step("Click registration link")
    public RegistrationPage clickRegistrationLink() {
        waitAndClick(registrationLink);
        return new RegistrationPage();
    }

    @Step("Check email validation message")
    public LoginPage checkEmailValidationMassage(final String message) {
        checkValidationMessage(emailInput, message);
        return this;
    }

    @Step("Check password validation message")
    public LoginPage checkPasswordValidationMessage(final String password) {
        checkValidationMessage(passwordInput, password);
        return this;
    }

    @Step("Check authorization error message")
    public LoginPage checkAuthorizationErrorMessage(final String message) {
        errorMessage.should(be(visible), have(exactText(message)));
        return this;
    }
}
