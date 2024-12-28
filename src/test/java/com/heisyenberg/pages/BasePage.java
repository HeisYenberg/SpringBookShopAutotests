package com.heisyenberg.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.have;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.heisyenberg.data.HeaderData.LOGO_IMAGE_URL;
import static com.heisyenberg.data.HeaderData.SHOP_TITLE;
import static com.heisyenberg.utils.ElementsUtil.checkImageSrc;
import static com.heisyenberg.utils.ElementsUtil.checkValidationMessage;
import static com.heisyenberg.utils.ElementsUtil.waitAndClick;

public abstract class BasePage {
    private final SelenideElement headerTitle = $(".navbar-brand");
    private final SelenideElement headerLogo = $(".navbar-brand > img");
    private final SelenideElement searchInput = $("#searchForm > input");
    private final SelenideElement searchSubmitButton = $("#searchForm > button");
    private final SelenideElement cartButton = $("a[href='/cart']");
    private final SelenideElement cartAmount = $(".icon-button > .badge");
    private final SelenideElement addBookButton = $("a[href='/addBook']");
    private final SelenideElement loginButton = $("a[href='/login']");
    private final SelenideElement logoutButton = $("a[href='/logout']");


    @Step("Check header")
    public BasePage checkHeader() {
        headerTitle.shouldHave(exactText(SHOP_TITLE));
        checkImageSrc(headerLogo, LOGO_IMAGE_URL);
        searchInput.shouldBe(visible);
        searchSubmitButton.shouldBe(visible);
        return this;
    }

    @Step("Check unauthorized header")
    public BasePage checkUnauthorizedHeader() {
        checkHeader();
        loginButton.shouldBe(visible);
        return this;
    }

    @Step("Check user role header: {username}")
    public BasePage checkUserRoleHeader(final String username) {
        logoutButton
                .shouldBe(visible)
                .shouldHave(exactText(username));
        cartButton.shouldBe(visible);
        return this;
    }

    @Step("Check admin role header: {username}")
    public BasePage checkAdminRoleHeader(final String username) {
        logoutButton
                .shouldBe(visible)
                .shouldHave(exactText(username));
        addBookButton.shouldBe(visible);
        return this;
    }

    @Step("Check cart amount: {amount}")
    public BasePage checkCartAmount(final int amount) {
        cartAmount
                .shouldBe(visible)
                .shouldHave(exactText(String.valueOf(amount)));
        return this;
    }

    @Step("Set search input: {text}")
    public BasePage setSearchInput(final String text) {
        searchInput.setValue(text);
        return this;
    }

    @Step("Click submit search button")
    public BasePage clickSubmitSearchButton() {
        searchSubmitButton.click();
        return this;
    }

    @Step("Check search validation message: message")
    public BasePage checkSearchValidationMessage(final String message) {
        checkValidationMessage(searchInput, message);
        return this;
    }

    @Step("Click cart button")
    public CartPage clickCartButton() {
        waitAndClick(cartButton);
        return new CartPage();
    }

    @Step("Click add book button")
    public AddBookPage clickAddBookButton() {
        waitAndClick(addBookButton);
        return new AddBookPage();
    }

    @Step("Click logo button")
    public BooksPage clickLogoButton() {
        waitAndClick(headerLogo);
        return new BooksPage();
    }

    @Step("Click login button")
    public LoginPage clickLoginButton() {
        waitAndClick(loginButton);
        return new LoginPage();
    }

    @Step("Click logout button")
    public BasePage clickLogoutButton() {
        waitAndClick(logoutButton);
        return this;
    }

    @Step("Check username: {username}")
    public BasePage checkUsername(final String username) {
        logoutButton.shouldBe(visible, have(exactText(username)));
        return this;
    }
}
