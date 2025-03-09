package com.ecommerce.dto;

import com.ecommerce.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailsDTO {
    private Long id;
    private List<CartItemDetailsDTO> cartItems;
}
