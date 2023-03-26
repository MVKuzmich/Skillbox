package com.example.simpleshop.repository;

import com.example.simpleshop.entity.MessageSending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageSendingRepository extends JpaRepository<MessageSending, Long> {
}
