package com.ecommerce.service;

import com.ecommerce.dto.CartDetailsDTO;
import com.ecommerce.dto.CartItemDetailsDTO;
import com.ecommerce.dto.CartItemRequestDTO;
import com.ecommerce.dto.CartItemUpdateDTO;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    ModelMapper modelMapper;

    @PostConstruct
    public void initMappings() {
        modelMapper.createTypeMap(CartItem.class, CartItemDetailsDTO.class)
                .addMapping(src -> src.getProduct().getId(), CartItemDetailsDTO::setProductId)
                .addMapping(src -> src.getProduct().getName(), CartItemDetailsDTO::setProductName)
                .addMapping(src -> src.getPrice(), CartItemDetailsDTO::setPrice)
                .addMapping(src -> src.getQuantity(), CartItemDetailsDTO::setQuantity);
    }


    @Override
    @Transactional
    public CartDetailsDTO getCartInformation(Long userid) {
        User user = userRepository
                .findById(userid)
                .orElseThrow(() -> new RuntimeException("Cart Scenario: user doesnot exist"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("No cart found"));
        CartDetailsDTO detailsDTO = modelMapper.map(cart, CartDetailsDTO.class);
        return detailsDTO;
    }

    @Override
    public CartDetailsDTO addCartItem(Long cartId, CartItemRequestDTO cartItemRequestDTO) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart Scenario: cart does not exist"));

        Product product = productRepository.findById(cartItemRequestDTO.getProductId()).orElseThrow(() -> new RuntimeException("Cart Scenario: product does not exist"));

        Optional<CartItem> cartItemExisting = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(cartItemRequestDTO.getProductId()))
                .findFirst();

        if(cartItemExisting.isPresent()){
            throw new RuntimeException("Product exists in cart. Use update api instead to change quantity");
        }

        CartItem cartItem = modelMapper.map(cartItemRequestDTO, CartItem.class);
        cartItem.setPrice(product.getPrice());
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cart.getCartItems().add(cartItem);
        cartRepository.save(cart);

        return modelMapper.map(cart, CartDetailsDTO.class);

    }

    @Override
    public CartDetailsDTO editCartItem(Long cartId, Long cartItemId, CartItemUpdateDTO cartItemUpdateDTO) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart Scenario: Invalid cart id"));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart Scenario: Invalid cart item id"));

        cartItem.setQuantity(cartItemUpdateDTO.getQuantity());

        cartRepository.save(cart);

        return modelMapper.map(cart, CartDetailsDTO.class);

    }

    @Override
    public CartDetailsDTO deleteCartItem(Long cartId, Long cartItemId){
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart Scenario: Invalid cart id"));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart Scenario: Invalid cart item id"));

        cart.getCartItems().remove(cartItem);
        cartRepository.save(cart);

        return modelMapper.map(cart,CartDetailsDTO.class);
    }

    @Override
    public CartDetailsDTO clearCart(Long cartId){
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart Scenario: Invalid cart id"));

        Iterator<CartItem> iterator = cart.getCartItems().iterator();
        while(iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }

        cartRepository.save(cart);
        return modelMapper.map(cart,CartDetailsDTO.class);
    }
}
