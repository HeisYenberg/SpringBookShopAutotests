package com.heisyenberg.tests;

import com.heisyenberg.models.CartItem;
import com.heisyenberg.pages.BooksPage;
import com.heisyenberg.pages.CartPage;
import com.heisyenberg.repositrories.CartRepository;
import com.heisyenberg.utils.JsonDeserializerUtil;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static com.heisyenberg.data.LoginData.USER_EMAIL;
import static com.heisyenberg.data.LoginData.USER_ID;
import static com.heisyenberg.data.LoginData.USER_PASSWORD;

@Feature("Cart page")
public class CartTest extends BaseTest {
    private CartPage cartPage;

    @BeforeMethod
    public void beforeMethod() {
        cartPage = open(BooksPage.PAGE_URL, BooksPage.class)
                .clickLoginButton()
                .authorize(USER_EMAIL, USER_PASSWORD)
                .clickCartButton()
                .checkOpen();
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        CartRepository.deleteCartItemsForUser(USER_ID);
    }

    @DataProvider
    public Object[][] dataProvider() {
        return new Object[][]{
                {"data/cart_item.json"},
                {"data/cart_item_with_quantity.json"},
                {"data/cart_items.json"}};
    }


    @Story("Check added items to cart")
    @Test(dataProvider = "dataProvider", description = "Check added items to cart")
    public void checkAddedItemsToCart(final String json) {
        List<CartItem> cartItems = JsonDeserializerUtil.readList(json, CartItem.class);
        CartRepository.saveCartItems(cartItems);
        refresh();
        cartPage.checkCartItems(cartItems);
    }

    @Story("Check item quantity increment, decrement")
    @Test(description = "Check item quantity increment, decrement")
    public void checkItemQuantityIncrementDecrement() {
        List<CartItem> cartItems = JsonDeserializerUtil.readList("data/cart_item.json", CartItem.class);
        CartRepository.saveCartItems(cartItems);
        refresh();
        int itemIndex = 0;
        cartPage
                .clickAndCheckIncrementQuantityButton(itemIndex)
                .clickAndCheckDecrementQuantityButton(itemIndex);
    }

    @Story("Check item removal")
    @Test(description = "Check item removal")
    public void checkItemRemoval() {
        List<CartItem> cartItems = JsonDeserializerUtil.readList("data/cart_items.json", CartItem.class);
        CartRepository.saveCartItems(cartItems);
        refresh();
        int itemIndex = 0;
        cartPage.clickItemRemoveButton(itemIndex);
    }
}
