package com.kuzmich.searchengineapp.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Index;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "page", indexes = @Index(columnList = "path, site_id", unique = true))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Page extends BaseEntity {

    @Column(name = "path", columnDefinition = "text")
    @NotNull
    private String path;
    @NotNull
    private int code;
    @Column(name = "content", columnDefinition = "text")
    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Site site;

    @Builder.Default
    @OneToMany(mappedBy = "page", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.kuzmich.searchengineapp.entity.Index> indexList = new ArrayList<>();


}
