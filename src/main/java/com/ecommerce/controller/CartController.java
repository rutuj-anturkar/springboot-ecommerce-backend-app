package com.ecommerce.controller;

import com.ecommerce.dto.CartItemRequestDTO;
import com.ecommerce.dto.CartItemUpdateDTO;
import com.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCartInformation(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartInformation(userId));
    }

    @PostMapping("/addItems/{cartId}")
    public ResponseEntity<?> addToCart(@PathVariable Long cartId, @RequestBody CartItemRequestDTO cartItemRequestDTO){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cartService.addCartItem(cartId, cartItemRequestDTO));
    }

    @PutMapping("/{cartId}/update/{cartItemId}")
    public ResponseEntity<?> updateCartItem(@PathVariable Long cartId, @PathVariable Long cartItemId, @RequestBody CartItemUpdateDTO cartItemUpdateDTO){
        return ResponseEntity.status(HttpStatus.OK).body(cartService.editCartItem(cartId, cartItemId, cartItemUpdateDTO));
    }

    @DeleteMapping("/{cartId}/update/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long cartId, @PathVariable Long cartItemId){
        return ResponseEntity.status(HttpStatus.OK).body(cartService.deleteCartItem(cartId, cartItemId));
    }

    @DeleteMapping("/clearCart/{cartId}")
    public ResponseEntity<?> clearCart(@PathVariable Long cartId){
        return ResponseEntity.ok(cartService.clearCart(cartId));
    }
}
