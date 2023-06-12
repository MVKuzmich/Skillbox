package com.example.bookshopapp.repository;

import com.example.bookshopapp.data.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query(value = "select u.id, u.hash, u.reg_time, u.balance, u.name from users u " +
            "join user_contact uc on u.id = uc.user_id " +
            "where uc.contact = :email", nativeQuery = true)
    UserEntity findUserByEmail(String email);
}
