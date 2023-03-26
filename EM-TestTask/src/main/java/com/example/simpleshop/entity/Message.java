package com.example.simpleshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString(exclude = {"messageSendingList"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "message_text")
    private String messageText;

    @OneToMany(mappedBy = "message")
    private List<MessageSending> messageSendingList = new ArrayList<>();

    public Message(String title, LocalDateTime createDate, String messageText) {
        this.title = title;
        this.createDate = createDate;
        this.messageText = messageText;
    }
}
