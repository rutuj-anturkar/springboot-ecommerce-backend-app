package com.ecommerce.controller;

import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder/user/{userId}")
    public ResponseEntity<?> placeOrderFromCart(@PathVariable Long userId){
        return ResponseEntity.ok(orderService.placeOrder(userId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long orderId){
        return ResponseEntity.ok(orderService.getOrderDetails(orderId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllOrdersByUser(@PathVariable Long userId){
        return ResponseEntity.ok(orderService.getALLOrderDetailsForUser(userId));
    }

    @PutMapping("/updateStatus/order/{orderId}/status/{status}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @PathVariable String status){
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId,status));
    }
}
