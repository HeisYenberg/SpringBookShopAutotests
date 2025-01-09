package com.heisyenberg.tests;

import static com.codeborne.selenide.Selenide.open;
import static com.heisyenberg.data.BooksData.BOOKS_NOT_FOUND_ALERT;
import static com.heisyenberg.data.BooksData.BOOK_AUTHOR_TO_SEARCH;
import static com.heisyenberg.data.BooksData.BOOK_GENRE_TO_SEARCH;
import static com.heisyenberg.data.BooksData.BOOK_TITLE_TO_SEARCH;
import static com.heisyenberg.data.BooksData.EMPTY_FIELD_ERROR;
import static com.heisyenberg.data.BooksData.INVALID_SEARCH_INPUT;
import static com.heisyenberg.data.BooksData.MAX_BOOKS_PER_PAGE;
import static com.heisyenberg.data.LoginData.USER_EMAIL;
import static com.heisyenberg.data.LoginData.USER_ID;
import static com.heisyenberg.data.LoginData.USER_PASSWORD;

import com.heisyenberg.models.Book;
import com.heisyenberg.pages.BooksPage;
import com.heisyenberg.pages.LoginPage;
import com.heisyenberg.repositrories.CartRepository;
import com.heisyenberg.utils.JsonDeserializerUtil;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import java.util.List;
import java.util.stream.Collectors;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Books page")
public class BooksTest extends BaseTest {
    private BooksPage booksPage;
    private List<Book> books;

    @BeforeClass
    public void setUp() {
        books = JsonDeserializerUtil.readList("data/books.json", Book.class);
    }

    @BeforeMethod
    public void openPage() {
        booksPage = open(BooksPage.PAGE_URL, BooksPage.class).checkOpen();
    }

    @Story("Check open login page")
    @Test(description = "Check open login page")
    public void checkOpenLoginPage() {
        booksPage
                .clickLoginButton()
                .checkOpen();
    }

    @Story("Check book cards")
    @Test(description = "Check book cards")
    public void checkBookCards() {
        booksPage.checkBooksByPages(books);
    }

    @Story("Check open book page")
    @Test(description = "Check open book page")
    public void checkOpenBookPage() {
        int bookIndex = 0;
        booksPage
                .clickBookByIndex(bookIndex)
                .checkBookOpen(books.get(bookIndex));
    }

    @Story("Check adding book to cart")
    @Test(description = "Check adding book to cart")
    public void checkAddingBookToCart() {
        int bookIndex = 0;
        booksPage.clickAddToCartButtonByIndex(bookIndex);
        new LoginPage().checkOpen();
    }

    @Story("Check adding books to cart as user")
    @Test(description = "Check adding books to cart as user")
    public void checkAddingBooksToCartAsUser() {
        booksPage
                .clickLoginButton()
                .authorize(USER_EMAIL, USER_PASSWORD);
        for (int i = 0; i < MAX_BOOKS_PER_PAGE; i++) {
            booksPage
                    .clickAddToCartButtonByIndex(i)
                    .checkCartAmount(i + 1);
        }
        booksPage.clickLogoutButton();
        CartRepository.deleteCartItemsForUser(USER_ID);
    }

    @Story("Check changing pages")
    @Test(description = "Check changing pages")
    public void checkChangingPages() {
        booksPage
                .clickNextPage()
                .clickPreviousPage();
    }

    @Story("Check search books by title")
    @Test(description = "Check search books by title")
    public void checkSearchBooksByTitle() {
        List<Book> searchedBooks = books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(BOOK_TITLE_TO_SEARCH))
                .collect(Collectors.toList());
        booksPage
                .setSearchInput(BOOK_TITLE_TO_SEARCH)
                .clickSubmitSearchButton();
        booksPage.checkBooksByPages(searchedBooks);
    }

    @Story("Check search books by author")
    @Test(description = "Check search books by author")
    public void checkSearchBooksByAuthor() {
        List<Book> searchedBooks = books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(BOOK_AUTHOR_TO_SEARCH))
                .collect(Collectors.toList());
        booksPage
                .setSearchInput(BOOK_AUTHOR_TO_SEARCH)
                .clickSubmitSearchButton();
        booksPage.checkBooksByPages(searchedBooks);
    }

    @Story("Check search books by genre")
    @Test(description = "Check search books by genre")
    public void checkSearchBooksByGenre() {
        List<Book> searchedBooks = books.stream()
                .filter(book -> book.getGenre().toLowerCase().contains(BOOK_GENRE_TO_SEARCH))
                .collect(Collectors.toList());
        booksPage
                .setSearchInput(BOOK_GENRE_TO_SEARCH)
                .clickSubmitSearchButton();
        booksPage.checkBooksByPages(searchedBooks);
    }

    @Story("Check search for invalid input")
    @Test(description = "Check search for invalid input")
    public void checkSearchForInvalidInput() {
        booksPage
                .setSearchInput(INVALID_SEARCH_INPUT)
                .clickSubmitSearchButton();
        booksPage
                .checkAlertMessage(BOOKS_NOT_FOUND_ALERT)
                .checkBooksByPages(books);
    }

    @Story("Check empty search input")
    @Test(description = "Check empty search input")
    public void checkEmptySearchInput() {
        booksPage
                .clickSubmitSearchButton()
                .checkSearchValidationMessage(EMPTY_FIELD_ERROR);
    }
}
