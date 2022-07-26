package com.kuzmich.searchengineapp.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Index;
import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "lemma", indexes = @Index(columnList = "lemma, site_id"))
public class Lemma extends BaseEntity {

    @NotNull
    private String lemma;
    @NotNull
    private int frequency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    @NotNull
    private Site site;

    @OneToMany(mappedBy = "lemma", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.kuzmich.searchengineapp.entity.Index> indexList = new ArrayList<>();

    public Lemma(String lemma, int frequency, Site site) {
        this.lemma = lemma;
        this.frequency = frequency;
        this.site = site;
    }

    @Override
    public String toString() {
        return lemma;
    }



}
