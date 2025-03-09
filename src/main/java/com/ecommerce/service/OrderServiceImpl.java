package com.ecommerce.service;

import com.ecommerce.dto.OrderDetailsDTO;
import com.ecommerce.dto.OrderItemDetailsDTO;
import com.ecommerce.model.*;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void initMappings() {
        modelMapper.createTypeMap(OrderItem.class, OrderItemDetailsDTO.class)
                .addMapping(src -> src.getProduct().getId(), OrderItemDetailsDTO::setProductId)
                .addMapping(src -> src.getProduct().getName(), OrderItemDetailsDTO::setProductName)
                .addMapping(src -> src.getPrice(), OrderItemDetailsDTO::setPrice)
                .addMapping(src -> src.getQuantity(), OrderItemDetailsDTO::setQuantity);
    }


    @Override
    public OrderDetailsDTO placeOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Order Scenario: user id is invalid"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Order Scenario: cart is unavailable"));

        Order order = new Order();
        order.setUser(user);

        List<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());

            return orderItem;
        }).collect(Collectors.toCollection(ArrayList::new));
        ;

        order.setOrderItems(orderItems);

        cart.getCartItems().clear();
        cartRepository.save(cart);

        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDetailsDTO.class);
    }

    @Override
    public OrderDetailsDTO getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order Scenario: Invalid order id"));
        return modelMapper.map(order, OrderDetailsDTO.class);
    }

    @Override
    public List<OrderDetailsDTO> getALLOrderDetailsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Order Scenario: user id is invalid"));

        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream()
                .map((order) -> modelMapper.map(order, OrderDetailsDTO.class))
                .toList();
    }

    @Override
    public OrderDetailsDTO updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order Scenario: Invalid order id"));
        order.setStatus(status);
        return modelMapper.map(orderRepository.save(order), OrderDetailsDTO.class);
    }
}
