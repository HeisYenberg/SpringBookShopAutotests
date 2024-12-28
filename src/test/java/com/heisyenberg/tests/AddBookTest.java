package com.heisyenberg.tests;

import com.heisyenberg.models.Book;
import com.heisyenberg.pages.AddBookPage;
import com.heisyenberg.pages.BooksPage;
import com.heisyenberg.repositrories.BooksRepository;
import com.heisyenberg.utils.DataGenerationUtil;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.heisyenberg.data.BookData.EMPTY_AUTHOR_ERROR_MESSAGE;
import static com.heisyenberg.data.BookData.EMPTY_DESCRIPTION_ERROR_MESSAGE;
import static com.heisyenberg.data.BookData.EMPTY_GENRE_ERROR_MESSAGE;
import static com.heisyenberg.data.BookData.EMPTY_IMAGE_FILE_ERROR_MESSAGE;
import static com.heisyenberg.data.BookData.EMPTY_PRICE_ERROR_MESSAGE;
import static com.heisyenberg.data.BookData.EMPTY_TITLE_ERROR_MESSAGE;
import static com.heisyenberg.data.BookData.IMAGE_FILE_PATH;
import static com.heisyenberg.data.BookData.PRICE_TO_LOW_ERROR_MESSAGE;
import static com.heisyenberg.data.LoginData.ADMIN_EMAIL;
import static com.heisyenberg.data.LoginData.ADMIN_PASSWORD;

@Feature("Add book test")
public class AddBookTest extends BaseTest {
    private AddBookPage addBookPage;
    private Book book;

    @BeforeMethod
    public void openPage() {
        addBookPage = open(BooksPage.PAGE_URL, BooksPage.class)
                .checkOpen()
                .clickLoginButton()
                .authorize(ADMIN_EMAIL, ADMIN_PASSWORD)
                .clickAddBookButton();
        book = DataGenerationUtil.generateBook();
    }

    @Story("Check book creation")
    @Test(description = "Check book creation")
    public void checkBookCreation() {
        addBookPage
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setGenre(book.getGenre())
                .setPrice(book.getPrice().toString())
                .setDescription(book.getDescription())
                .setImageFile(IMAGE_FILE_PATH)
                .clickAddButton();
        int bookIndex = 0;
        new BooksPage().checkItemCard(bookIndex, book);
        BooksRepository.deleteBook(book);
    }

    @Story("Check book creation with empty title")
    @Test(description = "Check book creation with empty title")
    public void checkBookCreationWithEmptyTitle() {
        addBookPage
                .clickAddButton()
                .checkTitleErrorMessage(EMPTY_TITLE_ERROR_MESSAGE);
    }

    @Story("Check book creation with empty author")
    @Test(description = "Check book creation with empty author")
    public void checkBookCreationWithEmptyAuthor() {
        addBookPage
                .setTitle(book.getTitle())
                .clickAddButton()
                .checkAuthorErrorMessage(EMPTY_AUTHOR_ERROR_MESSAGE);
    }

    @Story("Check book creation with empty genre")
    @Test(description = "Check book creation with empty genre")
    public void checkBookCreationWithEmptyGenre() {
        addBookPage
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .clickAddButton()
                .checkGenreErrorMessage(EMPTY_GENRE_ERROR_MESSAGE);
    }

    @Story("Check book creation with empty price")
    @Test(description = "Check book creation with empty price")
    public void checkBookCreationWithEmptyPrice() {
        addBookPage
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setGenre(book.getGenre())
                .clickAddButton()
                .checkPriceErrorMessage(EMPTY_PRICE_ERROR_MESSAGE);
    }

    @Story("Check book creation with to low price")
    @Test(description = "Check book creation with to low price")
    public void checkBookCreationWithToLowPrice() {
        String lowPrice = "10";
        addBookPage
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setGenre(book.getGenre())
                .setPrice(lowPrice)
                .clickAddButton()
                .checkPriceErrorMessage(PRICE_TO_LOW_ERROR_MESSAGE);
    }

    @Story("Check book creation with empty description")
    @Test(description = "Check book creation with empty description")
    public void checkBookCreationWithEmptyDescription() {
        addBookPage
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setGenre(book.getGenre())
                .setPrice(book.getPrice().toString())
                .clickAddButton()
                .checkDescriptionErrorMessage(EMPTY_DESCRIPTION_ERROR_MESSAGE);
    }

    @Story("Check book creation with empty image file")
    @Test(description = "Check book creation with empty image file")
    public void checkBookCreationWithEmptyImageFile() {
        addBookPage
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setGenre(book.getGenre())
                .setPrice(book.getPrice().toString())
                .setDescription(book.getDescription())
                .clickAddButton()
                .checkImageFileErrorMessage(EMPTY_IMAGE_FILE_ERROR_MESSAGE);
    }
}
