package com.example.simpleshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message_sending")
public class MessageSending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Message message;

    @Column(name = "send_date")
    private LocalDateTime sendDate;

    public MessageSending(User user, Message message, LocalDateTime sendDate) {
        this.user = user;
        this.message = message;
        this.sendDate = sendDate;
    }
}
