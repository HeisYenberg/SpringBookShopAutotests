package com.heisyenberg.repositrories;

import com.heisyenberg.models.CartItem;
import com.heisyenberg.utils.DatabaseUtil;
import lombok.experimental.UtilityClass;

import java.util.List;

import static com.heisyenberg.data.LoginData.USER_ID;

@UtilityClass
public final class CartRepository {
    public static void saveCartItem(final CartItem cartItem) {
        DatabaseUtil.update("INSERT INTO cart_items (user_id, book_id, quantity) VALUES (?, ?, ?)",
                USER_ID,
                cartItem.getBookId(),
                cartItem.getQuantity());
    }

    public static void saveCartItems(final List<CartItem> cartItems) {
        for (CartItem cartItem : cartItems) {
            saveCartItem(cartItem);
        }
    }

    public static void deleteCartItemsForUser(final int userId) {
        DatabaseUtil.update("DELETE FROM cart_items WHERE user_id = ?", userId);
    }
}
