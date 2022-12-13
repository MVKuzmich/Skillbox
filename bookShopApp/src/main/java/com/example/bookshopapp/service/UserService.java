package com.example.bookshopapp.service;

import com.example.bookshopapp.data.user.UserEntity;
import com.example.bookshopapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id).get();
    }

}
