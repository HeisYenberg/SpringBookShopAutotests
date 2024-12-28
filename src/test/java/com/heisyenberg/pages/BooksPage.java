package com.heisyenberg.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.heisyenberg.models.Book;
import io.qameta.allure.Step;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.be;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.have;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.confirm;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static com.heisyenberg.data.BooksData.ADD_TO_CART_BOOK_TEXT;
import static com.heisyenberg.data.BooksData.DELETE_BOOK_BUTTON_TEXT;
import static com.heisyenberg.data.BooksData.DELETION_CONFIRMATION_MESSAGE;
import static com.heisyenberg.data.BooksData.EDIT_BOOK_BUTTON_TEXT;
import static com.heisyenberg.data.BooksData.FIRST_PAGE_BOOKS_SIZE;
import static com.heisyenberg.data.BooksData.MAX_BOOKS_PER_PAGE;
import static com.heisyenberg.utils.ElementsUtil.checkImageSrc;
import static com.heisyenberg.utils.ElementsUtil.clickElementWithJavaScript;
import static com.heisyenberg.utils.ElementsUtil.scrollToElement;
import static com.heisyenberg.utils.ElementsUtil.waitAndClick;

public class BooksPage extends BasePage {
    public static final String PAGE_URL = "/books";

    private final ElementsCollection items = $$(".card");
    private final ElementsCollection images = $$(".card-img");
    private final ElementsCollection addToCartButtons = $$(".add-to-cart-btn");
    private final ElementsCollection deleteBookButtons = $$(".btn-danger");
    private final ElementsCollection editBookButtons = $$(".add-to-cart-btn");
    private final SelenideElement previousPageButton = $("a[aria-label='Previous']");
    private final SelenideElement nextPageButton = $("a[aria-label='Next']");
    private final SelenideElement currentPage = $("li.active");
    private final SelenideElement alertMessage = $(".alert-danger");

    @Step("Check books page open")
    public BooksPage checkOpen() {
        webdriver().shouldHave(urlContaining(PAGE_URL));
        checkHeader();
        items.shouldHave(size(FIRST_PAGE_BOOKS_SIZE));
        return this;
    }

    @Step("Click next page")
    public BooksPage clickNextPage() {
        int pageNumber = Integer.parseInt(currentPage.getText());
        clickElementWithJavaScript(nextPageButton);
        currentPage.shouldHave(exactText(String.valueOf(++pageNumber)));
        return this;
    }

    @Step("Click previous page")
    public BooksPage clickPreviousPage() {
        int pageNumber = Integer.parseInt(currentPage.getText());
        clickElementWithJavaScript(previousPageButton);
        currentPage.shouldHave(exactText(String.valueOf(--pageNumber)));
        return this;
    }

    @Step("Check books by pages")
    public BooksPage checkBooksByPages(final List<Book> books) {
        AtomicInteger index = new AtomicInteger(0);
        books.forEach(bookCard -> {
            checkItemCard(index.get(), bookCard);
            checkItemImage(index.get(), bookCard.getImageUrl());
            checkAddToCartButton(index.get());
            clickBookByIndex(index.get()).checkBookOpen(bookCard);
            back();
            index.incrementAndGet();
            if (index.get() == MAX_BOOKS_PER_PAGE) {
                clickNextPage();
                index.set(0);
            }
        });
        return this;
    }

    @Step("Check books by pages as admin")
    public BooksPage checkBooksByPagesAsAdmin(final List<Book> books) {
        AtomicInteger index = new AtomicInteger(0);
        books.forEach(bookCard -> {
            checkItemCard(index.get(), bookCard);
            checkItemImage(index.get(), bookCard.getImageUrl());
            checkDeleteBookButton(index.get());
            checkEditBookButton(index.get());
            clickBookByIndex(index.get()).checkBookOpenByAdmin(bookCard);
            back();
            index.incrementAndGet();
            if (index.get() == MAX_BOOKS_PER_PAGE) {
                clickNextPage();
                index.set(0);
            }
        });
        return this;
    }

    @Step("Click add book to cart by index: {index}")
    public BooksPage clickAddToCartButtonByIndex(final int index) {
        SelenideElement element = scrollToElement(addToCartButtons.get(index))
                .shouldBe(visible);
        waitAndClick(element);
        return this;
    }

    @Step("Click delete book by index: {index}")
    public BooksPage clickDeleteBookByIndex(final int index) {
        SelenideElement element = scrollToElement(deleteBookButtons.get(index))
                .shouldBe(visible);
        waitAndClick(element);
        confirm(DELETION_CONFIRMATION_MESSAGE);
        return this;
    }

    @Step("Click edit book by index: {index}")
    public EditBookPage clickEditBookByIndex(final int index) {
        SelenideElement element = scrollToElement(editBookButtons.get(index))
                .shouldBe(visible);
        waitAndClick(element);
        return new EditBookPage();
    }

    @Step("Click book by index: {index}")
    public BookPage clickBookByIndex(final int index) {
        scrollToElement(images.get(index))
                .hover()
                .shouldBe(visible);
        waitAndClick(images.get(index));
        return new BookPage();
    }

    @Step("Check item card: {card}")
    public BooksPage checkItemCard(final int index, final Book card) {
        SelenideElement element = items.get(index);
        ElementsCollection data = scrollToElement(element)
                .findAll(".card-text > span");
        element.find(".card-title").shouldHave(exactText(card.getTitle()));
        data.get(0).shouldHave(exactText(card.getGenre()));
        data.get(1).shouldHave(exactText(card.getAuthor()));
        data.get(2).shouldHave(exactText(card.getPrice().toString()));
        return this;
    }

    @Step("Check item image: {imageName}")
    public BooksPage checkItemImage(final int index, final String imageName) {
        String imagesPath = "/images/";
        checkImageSrc(images.get(index), imagesPath + imageName);
        return this;
    }

    @Step("Check add to cart button")
    public BooksPage checkAddToCartButton(final int index) {
        addToCartButtons
                .get(index)
                .hover()
                .should(be(visible), have(exactText(ADD_TO_CART_BOOK_TEXT)));
        return this;
    }

    @Step("Check delete book button")
    public BooksPage checkDeleteBookButton(final int index) {
        deleteBookButtons
                .get(index)
                .hover()
                .should(be(visible), have(exactText(DELETE_BOOK_BUTTON_TEXT)));
        return this;
    }

    @Step("Check edit book button")
    public BooksPage checkEditBookButton(final int index) {
        editBookButtons
                .get(index)
                .hover()
                .should(be(visible), have(exactText(EDIT_BOOK_BUTTON_TEXT)));
        return this;
    }

    @Step("Check alert message: {message}")
    public BooksPage checkAlertMessage(final String message) {
        alertMessage.should(be(visible), have(exactText(message)));
        return this;
    }
}
