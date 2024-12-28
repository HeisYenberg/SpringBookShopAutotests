package com.heisyenberg.pages;

import com.codeborne.selenide.SelenideElement;
import com.heisyenberg.models.Book;
import io.qameta.allure.Step;

import java.io.File;

import static com.codeborne.selenide.Condition.be;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.have;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.heisyenberg.utils.ElementsUtil.waitAndClick;

public class EditBookPage extends BasePage {
    private final SelenideElement title = $("h2");
    private final SelenideElement authorInput = $("#author");
    private final SelenideElement authorErrorMessage = $("#author ~ .text-danger");
    private final SelenideElement genreInput = $("#genre");
    private final SelenideElement genreErrorMessage = $("#genre ~ .text-danger");
    private final SelenideElement priceInput = $("#price");
    private final SelenideElement priceErrorMessage = $("#price ~ .text-danger");
    private final SelenideElement descriptionInput = $("#description");
    private final SelenideElement descriptionErrorMessage = $("#description ~ .text-danger");
    private final SelenideElement imageFileInput = $("#imageFile");
    private final SelenideElement saveEditButton = $(".btn-primary");
    private final SelenideElement cancelButton = $(".btn-cancel");

    @Step("Check edit book open: {book}")
    public EditBookPage checkEditBookOpen(final Book book) {
        title.should(be(visible), have(exactText(book.getTitle())));
        authorInput.should((visible), have(exactValue(book.getAuthor())));
        genreInput.should(be(visible), have(exactValue(book.getGenre())));
        priceInput.should(be(visible), have(exactValue(book.getPrice().toString())));
        descriptionInput.should(be(visible), have(exactValue(book.getDescription())));
        imageFileInput.shouldBe(visible);
        return this;
    }

    @Step("Set book author: {author}")
    public EditBookPage setBookAuthor(final String author) {
        authorInput.setValue(author);
        return this;
    }

    @Step("Set book genre: {genre}")
    public EditBookPage setBookGenre(final String genre) {
        genreInput.setValue(genre);
        return this;
    }

    @Step("Set book price: {price}")
    public EditBookPage setBookPrice(final String price) {
        priceInput.setValue(price);
        return this;
    }

    @Step("Set book description: {description}")
    public EditBookPage setBookDescription(final String description) {
        descriptionInput.setValue(description);
        return this;
    }

    @Step("Set book image: {imageFilePath}")
    public EditBookPage setBookImage(final String imageFilePath) {
        imageFileInput.uploadFile(new File(imageFilePath));
        return this;
    }

    @Step("Click save edit button")
    public EditBookPage clickSaveEditButton() {
        waitAndClick(saveEditButton);
        return this;
    }

    @Step("Click cancel edit button")
    public BooksPage clickCancelEditButton() {
        waitAndClick(cancelButton);
        return new BooksPage();
    }

    @Step("Check author error message: {message}")
    public EditBookPage checkAuthorErrorMessage(final String message) {
        authorErrorMessage.should(be(visible), have(exactText(message)));
        return this;
    }

    @Step("Check genre error message: {message}")
    public EditBookPage checkGenreErrorMessage(final String message) {
        genreErrorMessage.should(be(visible), have(exactText(message)));
        return this;
    }

    @Step("Check price error message: {message}")
    public EditBookPage checkPriceErrorMessage(final String message) {
        priceErrorMessage.should(be(visible), have(exactText(message)));
        return this;
    }

    @Step("Check description error message: {message}")
    public EditBookPage checkDescriptionErrorMessage(final String message) {
        descriptionErrorMessage.should(be(visible), have(exactText(message)));
        return this;
    }
}
