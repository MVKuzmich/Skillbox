package com.kuzmich.searchengineapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "index_table", indexes = @javax.persistence.Index(columnList = "lemma_id"))
public class Index extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id")
    @NotNull
    private Page page;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lemma_id")
    @NotNull
    private Lemma lemma;
    @NotNull
    @Column(name = "rank_lemma")
    private float rank;

    public Index(Page page, Lemma lemma, float rank) {
        this.page = page;
        this.lemma = lemma;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Index: pageId " + page.getId() + " - " + "lemmaId " + lemma.getId();
    }
}
