package com.museum.ticketsystem.service;

import com.museum.ticketsystem.model.User;
import com.museum.ticketsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String registerUser(User user) {
        if (userRepository.findByIdNumber(user.getIdNumber()) != null) {
            return "User with this ID number already exists!";
        }

        userRepository.save(user);
        return "User registered successfully!";

    }

    public String loginUser(User user) {
        User foundUser = userRepository.findByIdNumber(user.getIdNumber());
        if (foundUser != null && foundUser.getName().equals(user.getName())) {
            return "Login successful!";
        }
        return "Invalid user credentials!";
    }

    // 添加此方法
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
