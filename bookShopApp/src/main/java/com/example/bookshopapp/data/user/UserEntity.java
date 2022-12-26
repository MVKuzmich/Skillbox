package com.example.bookshopapp.data.user;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.data.book.review.BookReviewEntity;
import com.example.bookshopapp.data.book.review.BookReviewLikeEntity;
import com.example.bookshopapp.data.book.review.MessageEntity;
import com.example.bookshopapp.data.enums.ContactType;
import com.example.bookshopapp.data.payments.BalanceTransactionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String hash;

    @Column(name = "reg_time", columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime regTime;

    @Column(columnDefinition = "INT NOT NULL")
    private int balance;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;

    @ManyToMany(mappedBy = "userSet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Book> bookSet;

    @ManyToMany(mappedBy = "userDownloadBookSet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Book> bookDownloadedUserSet;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BalanceTransactionEntity> balanceTransactionEntitySet;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BookReviewEntity> userReviewSet;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MessageEntity> messageSet;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserContactEntity> userContactSet = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BookReviewLikeEntity> userLikeSet;

    public UserEntity(String hash, LocalDateTime regTime, int balance, String name) {
        this.hash = hash;
        this.regTime = regTime;
        this.balance = balance;
        this.name = name;
    }

    public String getEmail() {
        Optional<UserContactEntity> contactOptional = userContactSet.stream()
                .filter(contact -> contact.getType() == ContactType.EMAIL)
                .findFirst();
        return contactOptional.isPresent() ? contactOptional.get().getContact() : "EMAIL was not confirmed";
    }

    public String getPhone() {
        Optional<UserContactEntity> contactOptional = userContactSet.stream()
                .filter(contact -> contact.getType() == ContactType.PHONE)
                .findFirst();
        return contactOptional.isPresent() ? contactOptional.get().getContact() : "PHONE was not confirmed";
    }
}

