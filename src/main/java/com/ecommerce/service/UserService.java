package com.ecommerce.service;

import com.ecommerce.dto.UserLoginRequestDTO;
import com.ecommerce.dto.UserLoginResponseDTO;
import com.ecommerce.dto.UserRegistrationDTO;

public interface UserService {
    public void registerUser(UserRegistrationDTO registrationDTO);

    public UserLoginResponseDTO loginUser(UserLoginRequestDTO requestDTO);

    boolean isLoginValid(String username, String password);
}
