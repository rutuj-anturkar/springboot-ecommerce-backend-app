package com.ecommerce.service;

import com.ecommerce.dto.OrderDetailsDTO;

import java.util.List;

public interface OrderService {
    OrderDetailsDTO placeOrder(Long userId);

    OrderDetailsDTO getOrderDetails(Long orderId);

    List<OrderDetailsDTO> getALLOrderDetailsForUser(Long userId);

    OrderDetailsDTO updateOrderStatus(Long orderId, String status);
}
