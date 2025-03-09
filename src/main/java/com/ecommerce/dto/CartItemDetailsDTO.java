package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDetailsDTO {
    private Long id;
    private Long productId;
    private String productName;
    private Long quantity;
    private BigDecimal price;
}
