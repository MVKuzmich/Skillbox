package com.kuzmich.searchengineapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Field extends BaseEntity {

    @NotNull
    private String name;
    @NotNull
    private String selector;
    @NotNull
    private float weight;


}
