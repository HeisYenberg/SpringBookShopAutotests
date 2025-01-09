package com.heisyenberg.tests;

import static com.codeborne.selenide.Selenide.open;
import static com.heisyenberg.data.BookData.EMPTY_AUTHOR_ERROR_MESSAGE;
import static com.heisyenberg.data.BookData.EMPTY_DESCRIPTION_ERROR_MESSAGE;
import static com.heisyenberg.data.BookData.EMPTY_GENRE_ERROR_MESSAGE;
import static com.heisyenberg.data.BookData.EMPTY_PRICE_ERROR_MESSAGE;
import static com.heisyenberg.data.BookData.IMAGE_FILE_PATH;
import static com.heisyenberg.data.BookData.PRICE_TO_LOW_ERROR_MESSAGE;
import static com.heisyenberg.data.LoginData.ADMIN_EMAIL;
import static com.heisyenberg.data.LoginData.ADMIN_PASSWORD;

import com.heisyenberg.models.Book;
import com.heisyenberg.pages.BooksPage;
import com.heisyenberg.pages.EditBookPage;
import com.heisyenberg.repositrories.BooksRepository;
import com.heisyenberg.utils.DataGenerationUtil;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Edit book page")
public class EditBookTest extends BaseTest {
    private EditBookPage editBookPage;
    private Book book;

    @BeforeMethod
    public void beforeMethod() {
        book = DataGenerationUtil.generateBook();
        BooksRepository.saveBook(book);
        editBookPage = open(BooksPage.PAGE_URL, BooksPage.class)
                .clickLoginButton()
                .authorize(ADMIN_EMAIL, ADMIN_PASSWORD)
                .clickEditBookByIndex(0)
                .checkEditBookOpen(book);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        BooksRepository.deleteBook(book);
    }

    @Story("Check edit book without image update")
    @Test(description = "Check edit book without image update")
    public void checkEditBookWithoutImageUpdate() {
        int bookIndex = 0;
        String title = book.getTitle();
        book = DataGenerationUtil.generateBook();
        book.setTitle(title);
        editBookPage
                .setBookAuthor(book.getAuthor())
                .setBookGenre(book.getGenre())
                .setBookPrice(book.getPrice().toString())
                .setBookDescription(book.getDescription())
                .clickSaveEditButton();
        new BooksPage()
                .clickBookByIndex(bookIndex)
                .checkBookOpen(book);
    }

    @Story("Check edit book with image update")
    @Test(description = "Check edit book with image update")
    public void checkEditBookWithImageUpdate() {
        int bookIndex = 0;
        String title = book.getTitle();
        book = DataGenerationUtil.generateBook();
        book.setTitle(title);
        editBookPage
                .setBookAuthor(book.getAuthor())
                .setBookGenre(book.getGenre())
                .setBookPrice(book.getPrice().toString())
                .setBookDescription(book.getDescription())
                .setBookImage(IMAGE_FILE_PATH)
                .clickSaveEditButton();
        new BooksPage()
                .clickBookByIndex(bookIndex)
                .checkBookOpen(book);
    }

    @Story("Check edit book with empty author")
    @Test(description = "Check edit book with empty author")
    public void checkEditBookWithEmptyAuthor() {
        editBookPage
                .setBookAuthor("")
                .clickSaveEditButton()
                .checkAuthorErrorMessage(EMPTY_AUTHOR_ERROR_MESSAGE);
    }

    @Story("Check edit book with empty genre")
    @Test(description = "Check edit book with empty genre")
    public void checkEditBookWithEmptyGenre() {
        editBookPage
                .setBookGenre("")
                .clickSaveEditButton()
                .checkGenreErrorMessage(EMPTY_GENRE_ERROR_MESSAGE);
    }

    @Story("Check edit book with empty price")
    @Test(description = "Check edit book with empty price")
    public void checkEditBookWithEmptyPrice() {
        editBookPage
                .setBookPrice("")
                .clickSaveEditButton()
                .checkPriceErrorMessage(EMPTY_PRICE_ERROR_MESSAGE);
    }

    @Story("Check edit book with price to low")
    @Test(description = "Check edit book with price to low")
    public void checkEditBookWithPriceToLow() {
        editBookPage
                .setBookPrice("10")
                .clickSaveEditButton()
                .checkPriceErrorMessage(PRICE_TO_LOW_ERROR_MESSAGE);
    }

    @Story("Check edit book with empty description")
    @Test(description = "Check edit book with empty description")
    public void checkEditBookWithEmptyDescription() {
        editBookPage
                .setBookDescription("")
                .clickSaveEditButton()
                .checkDescriptionErrorMessage(EMPTY_DESCRIPTION_ERROR_MESSAGE);
    }
}
