package com.example.bookshopapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserContactConfirmationPayload {

    private String contact;
    private String code;

}
