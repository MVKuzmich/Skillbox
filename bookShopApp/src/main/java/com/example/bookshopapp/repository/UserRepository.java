package com.example.bookshopapp.repository;

import com.example.bookshopapp.data.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}
