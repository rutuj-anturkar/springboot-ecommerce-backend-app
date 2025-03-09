package com.ecommerce.dto;

import com.ecommerce.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDTO {
    private Long id;
    private String status;
    private List<OrderItemDetailsDTO> orderItems;
}
