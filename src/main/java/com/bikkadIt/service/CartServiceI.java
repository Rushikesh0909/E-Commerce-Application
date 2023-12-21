package com.bikkadIt.service;

import com.bikkadIt.dto.AddItemToCartREquest;
import com.bikkadIt.dto.CartDto;

public interface CartServiceI {

    // add item to cart
    CartDto addItemToCart(String userId, AddItemToCartREquest request);

    // remove item from cart
    void removeItemFromCart(String userId ,Integer itemId);

    // remove all items from cart
    void clearCart(String userId);

    CartDto getCartByUser(String userId);

}
