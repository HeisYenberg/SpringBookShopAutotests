package com.heisyenberg.pages;

import static com.codeborne.selenide.Condition.be;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.have;
import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.heisyenberg.data.BookData.ADD_BOOK_HEADER;
import static com.heisyenberg.utils.ElementsUtil.waitAndClick;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.io.File;

public class AddBookPage extends BasePage {
    private final SelenideElement header = $("h2");
    private final SelenideElement titleInput = $("#title");
    private final SelenideElement titleErrorMessage = $("#title ~ .text-danger");
    private final SelenideElement authorInput = $("#author");
    private final SelenideElement authorErrorMessage = $("#author ~ .text-danger");
    private final SelenideElement genreInput = $("#genre");
    private final SelenideElement genreErrorMessage = $("#genre ~ .text-danger");
    private final SelenideElement priceInput = $("#price");
    private final SelenideElement priceErrorMessage = $("#price ~ .text-danger");
    private final SelenideElement descriptionInput = $("#description");
    private final SelenideElement descriptionErrorMessage = $("#description ~ .text-danger");
    private final SelenideElement imageFileInput = $("#imageFile");
    private final SelenideElement imageErrorMessage = $("#imageFile ~ .text-danger");
    private final SelenideElement addButton = $(".btn-primary");
    private final SelenideElement cancelButton = $(".btn-cancel");

    @Step("Check add book page open")
    public AddBookPage checkAddBookPage() {
        header.should(be(visible), have(exactText(ADD_BOOK_HEADER)));
        titleInput.shouldBe(visible);
        authorInput.shouldBe(visible);
        genreInput.shouldBe(visible);
        priceInput.shouldBe(visible);
        descriptionInput.shouldBe(visible);
        imageFileInput.shouldBe(visible);
        addButton.shouldBe(visible, interactable);
        cancelButton.shouldBe(visible, interactable);
        return this;
    }

    @Step("Set book title: {title}")
    public AddBookPage setTitle(final String title) {
        titleInput.setValue(title);
        return this;
    }

    @Step("Set book author: {author}")
    public AddBookPage setAuthor(final String author) {
        authorInput.setValue(author);
        return this;
    }

    @Step("Set book genre: {genre}")
    public AddBookPage setGenre(final String genre) {
        genreInput.setValue(genre);
        return this;
    }

    @Step("Set book price: {price}")
    public AddBookPage setPrice(final String price) {
        priceInput.setValue(price);
        return this;
    }

    @Step("Set book description: {description}")
    public AddBookPage setDescription(final String description) {
        descriptionInput.setValue(description);
        return this;
    }

    @Step("Set book image file: {imageFilePath}")
    public AddBookPage setImageFile(final String imageFilePath) {
        imageFileInput.uploadFile(new File(imageFilePath));
        return this;
    }

    @Step("Click add book button")
    public AddBookPage clickAddButton() {
        waitAndClick(addButton);
        return this;
    }

    @Step("Click cancel button")
    public BooksPage clickCancelButton() {
        waitAndClick(cancelButton);
        return new BooksPage();
    }

    @Step("Check title error message: {message}")
    public AddBookPage checkTitleErrorMessage(final String message) {
        titleErrorMessage.should(be(visible), have(exactText(message)));
        return this;
    }

    @Step("Check author error message: {message}")
    public AddBookPage checkAuthorErrorMessage(final String message) {
        authorErrorMessage.should(be(visible), have(exactText(message)));
        return this;
    }

    @Step("Check genre error message: {message}")
    public AddBookPage checkGenreErrorMessage(final String message) {
        genreErrorMessage.should(be(visible), have(exactText(message)));
        return this;
    }

    @Step("Check price error message: {message}")
    public AddBookPage checkPriceErrorMessage(final String message) {
        priceErrorMessage.should(be(visible), have(exactText(message)));
        return this;
    }

    @Step("Check description error message: {message}")
    public AddBookPage checkDescriptionErrorMessage(final String message) {
        descriptionErrorMessage.should(be(visible), have(exactText(message)));
        return this;
    }

    @Step("Check image file error message: {message}")
    public AddBookPage checkImageFileErrorMessage(final String message) {
        imageErrorMessage.should(be(visible), have(exactText(message)));
        return this;
    }
}
