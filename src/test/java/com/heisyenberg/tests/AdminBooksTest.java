package com.heisyenberg.tests;

import com.heisyenberg.models.Book;
import com.heisyenberg.pages.BooksPage;
import com.heisyenberg.repositrories.BooksRepository;
import com.heisyenberg.utils.DataGenerationUtil;
import com.heisyenberg.utils.JsonDeserializerUtil;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static com.heisyenberg.data.LoginData.ADMIN_EMAIL;
import static com.heisyenberg.data.LoginData.ADMIN_PASSWORD;
import static com.heisyenberg.data.LoginData.ADMIN_USERNAME;

@Feature("Admin books page")
public class AdminBooksTest extends BaseTest {
    private List<Book> books;
    private BooksPage booksPage;

    @BeforeClass
    public void getBooks() {
        books = JsonDeserializerUtil.readList("data/books.json", Book.class);
    }

    @BeforeMethod
    public void openPage() {
        booksPage = open(BooksPage.PAGE_URL, BooksPage.class)
                .checkOpen()
                .clickLoginButton()
                .authorize(ADMIN_EMAIL, ADMIN_PASSWORD);
        booksPage.checkAdminRoleHeader(ADMIN_USERNAME);
    }

    @Story("Check book cards as admin")
    @Test(description = "Check book cards as admin")
    public void checkBookCardsAsAdmin() {
        booksPage.checkBooksByPagesAsAdmin(books);
    }

    @Story("Check delete book")
    @Test(description = "Check delete book")
    public void checkDeleteBook() {
        Book book = DataGenerationUtil.generateBook();
        BooksRepository.saveBook(book);
        refresh();
        int bookIndex = 0;
        booksPage.clickDeleteBookByIndex(bookIndex);
    }

    @Story("Check edit book")
    @Test(description = "Check edit book")
    public void checkEditBook() {
        int bookIndex = 0;
        booksPage
                .clickEditBookByIndex(bookIndex)
                .checkEditBookOpen(books.get(bookIndex))
                .clickLogoButton();
    }
}
