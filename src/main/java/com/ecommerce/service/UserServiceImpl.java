package com.ecommerce.service;

import com.ecommerce.dto.UserLoginRequestDTO;
import com.ecommerce.dto.UserLoginResponseDTO;
import com.ecommerce.dto.UserRegistrationDTO;
import com.ecommerce.model.Cart;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void registerUser(UserRegistrationDTO registrationDTO) {
        if(!userRepository.findByUsername(registrationDTO.getUsername()).isEmpty()){
            throw new RuntimeException("User already exists");
        }
        User user = modelMapper.map(registrationDTO, User.class);
        user.addCart(new Cart());
        userRepository.save(user);
    }

    @Override
    public UserLoginResponseDTO loginUser(UserLoginRequestDTO requestDTO) {
        return modelMapper.map(userRepository.findByUsernameAndPassword(requestDTO.getUsername(),requestDTO.getPassword())
                .orElseThrow(()->new RuntimeException("Invalid Login")),UserLoginResponseDTO.class);
    }

    @Override
    public boolean isLoginValid(String username, String password){
        modelMapper.map(userRepository.findByUsernameAndPassword(username,password)
                .orElseThrow(()->new RuntimeException("Invalid Login")),UserLoginResponseDTO.class);
        return true;
    }
}
