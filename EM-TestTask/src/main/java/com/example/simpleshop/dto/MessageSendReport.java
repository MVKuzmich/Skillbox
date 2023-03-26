package com.example.simpleshop.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class MessageSendReport {

    Long messageId;
    String title;
    LocalDateTime createDate;
    String messageText;
    Long receiverCount;

}
