package com.ecommerce.service;

import com.ecommerce.dto.CartDetailsDTO;
import com.ecommerce.dto.CartItemRequestDTO;
import com.ecommerce.dto.CartItemUpdateDTO;

public interface CartService {
    CartDetailsDTO getCartInformation(Long userid);

    CartDetailsDTO addCartItem(Long cartId, CartItemRequestDTO cartItemRequestDTO);

    CartDetailsDTO editCartItem(Long cartId, Long cartItemId, CartItemUpdateDTO cartItemUpdateDTO);

    CartDetailsDTO deleteCartItem(Long cartId, Long cartItemId);

    CartDetailsDTO clearCart(Long cartId);
}
