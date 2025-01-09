package com.heisyenberg.pages;

import static com.codeborne.selenide.Condition.be;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.have;
import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.heisyenberg.models.Book;
import io.qameta.allure.Step;

public class BookPage extends BasePage {
    private final SelenideElement title = $(".card-title");
    private final ElementsCollection bookData = $$("p > span");
    private final SelenideElement genre = bookData.get(0);
    private final SelenideElement author = bookData.get(1);
    private final SelenideElement price = bookData.get(2);
    private final SelenideElement description = $("picture");
    private final SelenideElement addToCartButton = $(".btn-buy");
    private final SelenideElement deleteBookButton = $(".btn-delete");
    private final SelenideElement editBookButton = $("a[href*='/editBook/']");

    @Step("Check book open")
    public BookPage checkBookOpen(final Book book) {
        title.should(be(visible), have(exactText(book.getTitle())));
        genre.should(be(visible), have(exactText(book.getGenre())));
        author.should(be(visible), have(exactText(book.getAuthor())));
        price.should(be(visible), have(exactText(book.getPrice().toString())));
        description.should(be(visible), have(exactText(book.getDescription())));
        addToCartButton.shouldBe(visible, interactable);
        return this;
    }

    @Step("Check book open by admin")
    public BookPage checkBookOpenByAdmin(final Book book) {
        title.should(be(visible), have(exactText(book.getTitle())));
        genre.should(be(visible), have(exactText(book.getGenre())));
        author.should(be(visible), have(exactText(book.getAuthor())));
        price.should(be(visible), have(exactText(book.getPrice().toString())));
        description.should(be(visible), have(exactText(book.getDescription())));
        deleteBookButton.shouldBe(visible, interactable);
        editBookButton.shouldBe(visible, interactable);
        return this;
    }
}
