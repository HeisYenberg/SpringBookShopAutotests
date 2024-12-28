package com.heisyenberg.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.be;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.have;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.heisyenberg.utils.ElementsUtil.checkValidationMessage;
import static com.heisyenberg.utils.ElementsUtil.waitAndClick;

public class RegistrationPage extends BasePage {
    public static final String PAGE_URL = "/registration";

    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement usernameError = $("#username ~ .text-danger");
    private final SelenideElement emailInput = $("#email");
    private final SelenideElement emailError = $("#email ~ .text-danger");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement passwordError = $("#password ~ .text-danger");
    private final SelenideElement registrationSubmitButton = $(".btn-primary");
    private final SelenideElement loginLink = $("a[href='/login']");

    @Step("Check registration page open")
    public RegistrationPage checkOpen() {
        checkUnauthorizedHeader();
        usernameInput.shouldBe(visible);
        emailInput.shouldBe(visible);
        passwordInput.shouldBe(visible);
        registrationSubmitButton.shouldBe(visible);
        loginLink.shouldBe(visible);
        return this;
    }

    @Step("Set username: {username}")
    public RegistrationPage setUsername(final String username) {
        usernameInput.setValue(username);
        return this;
    }

    @Step("Set email: {email}")
    public RegistrationPage setEmail(final String email) {
        emailInput.setValue(email);
        return this;
    }

    @Step("Set password: {password}")
    public RegistrationPage setPassword(final String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Click registration submit button")
    public RegistrationPage clickRegistrationSubmitButton() {
        waitAndClick(registrationSubmitButton);
        return this;
    }

    @Step("Check username validation message: {message}")
    public RegistrationPage checkUsernameValidationMessage(final String message) {
        checkValidationMessage(usernameInput, message);
        return this;
    }

    @Step("Check email validation message: {message}")
    public RegistrationPage checkEmailValidationMessage(final String message) {
        checkValidationMessage(emailInput, message);
        return this;
    }

    @Step("Check password validation message: {message}")
    public RegistrationPage checkPasswordValidationMessage(final String message) {
        checkValidationMessage(passwordInput, message);
        return this;
    }

    @Step("Check username error message: {message}")
    public RegistrationPage checkUsernameErrorMessage(final String message) {
        usernameError.should(be(visible), have(exactText(message)));
        return this;
    }

    @Step("Check email error message: {message}")
    public RegistrationPage checkEmailErrorMessage(final String message) {
        emailError.should(be(visible), have(exactText(message)));
        return this;
    }

    @Step("Check password error message: {message}")
    public RegistrationPage checkPasswordErrorMessage(final String message) {
        passwordError.should(be(visible), have(exactText(message)));
        return this;
    }
}
