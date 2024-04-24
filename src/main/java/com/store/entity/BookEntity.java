package com.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "book")
public class BookEntity extends BaseEntity {
    @Column(name = "name")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    private ProfileEntity author;
    @Column(name = "author_id")
    private String authorId;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<PurchaseEntity> purchase;

    // kitob necha marotaba sotilgani
    @Column(name = "sales_count")
    private Long salesCount;

    // commentlar soni
    @Column(name = "comment_count")
    private Long commentCount;

    // view count
    @Column(name = "view_count")
    private Long viewCount;
}
