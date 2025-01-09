package com.heisyenberg.pages;

import static com.codeborne.selenide.Condition.be;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.have;
import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.heisyenberg.data.CartData.CHECKOUT_BUTTON_LABEL;
import static com.heisyenberg.data.CartData.HEADER_TITLE;
import static com.heisyenberg.data.CartData.TOTAL_SUM_LABEL_FORMAT;
import static com.heisyenberg.utils.ElementsUtil.checkImageSrc;
import static com.heisyenberg.utils.ElementsUtil.scrollToElement;
import static com.heisyenberg.utils.ElementsUtil.waitAndClick;
import static java.lang.String.format;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.heisyenberg.models.CartItem;
import io.qameta.allure.Step;
import java.util.List;

public class CartPage extends BasePage {
    private final SelenideElement headerTitle = $("h2");
    private final ElementsCollection cartItems = $$("tbody > tr");
    private final ElementsCollection itemImages = $$("td > img");
    private final ElementsCollection decrementQuantityButtons = $$("td [value='decrement']");
    private final ElementsCollection incrementQuantityButtons = $$("td [value='increment']");
    private final ElementsCollection itemQuantityLabels = $$("td  label.page-link");
    private final ElementsCollection removeItemButtons = $$("td > [action*='/removeFromCart/']");
    private final SelenideElement checkoutButton = $(".btn-success");
    private final SelenideElement totalSumLabel = $(".total-sum");

    public CartPage checkOpen() {
        headerTitle.should(be(visible), have(exactText(HEADER_TITLE)));
        checkoutButton.should(be(visible), be(interactable), have(exactText(CHECKOUT_BUTTON_LABEL)));
        return this;
    }

    @Step("Check cart items")
    public CartPage checkCartItems(List<CartItem> cartItems) {
        for (int i = 0; i < cartItems.size(); i++) {
            checkCartItem(i, cartItems.get(i));
        }
        double totalSum = cartItems
                .stream()
                .mapToDouble(it -> it.getBookPrice() * it.getQuantity())
                .sum();
        totalSumLabel.should(be(visible), have(exactText(format(TOTAL_SUM_LABEL_FORMAT, totalSum))));
        return this;
    }

    @Step("Check cart item: {cartItem}")
    public CartPage checkCartItem(final int index, final CartItem cartItem) {
        String imagesPath = "/images/";
        ElementsCollection data = scrollToElement(cartItems.get(index)).findAll("td");
        double totalSum = Double.parseDouble(itemQuantityLabels.get(index).text()) * cartItem.getBookPrice();
        data.get(1).should(be(visible), have(exactText(cartItem.getBookTitle())));
        data.get(2).should(be(visible), have(exactText(cartItem.getBookPrice().toString())));
        data.get(4).should(be(visible), have(exactText(Double.toString(totalSum))));
        checkImageSrc(itemImages.get(index), imagesPath + cartItem.getImageUrl());
        decrementQuantityButtons.get(index).shouldBe(visible);
        incrementQuantityButtons.get(index).shouldBe(visible);
        removeItemButtons.get(index).shouldBe(visible, interactable);
        return this;
    }

    @Step("Click and check item quantity decrement button")
    public CartPage clickAndCheckDecrementQuantityButton(final int index) {
        int quantity = Integer.parseInt(itemQuantityLabels.get(index).text());
        waitAndClick(decrementQuantityButtons.get(index));
        itemQuantityLabels.get(index).shouldHave(exactText(Integer.toString(--quantity)));
        return this;
    }

    @Step("Click and check item quantity increment button")
    public CartPage clickAndCheckIncrementQuantityButton(final int index) {
        int quantity = Integer.parseInt(itemQuantityLabels.get(index).text());
        waitAndClick(incrementQuantityButtons.get(index));
        itemQuantityLabels.get(index).shouldHave(exactText(Integer.toString(++quantity)));
        return this;
    }

    @Step("Click item remove button")
    public CartPage clickItemRemoveButton(final int index) {
        int size = cartItems.size();
        waitAndClick(removeItemButtons.get(index));
        cartItems.shouldHave(CollectionCondition.sizeLessThan(size));
        return this;
    }
}
