package com.store.entity;

import com.store.enums.ProfileRole;
import com.store.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "login", nullable = false, unique = true)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ProfileRole role;
    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private List<PurchaseEntity> purchase;

    // necha marta kitob sotib olgani
    @Column(name = "purchases_count")
    private Long purchasesCount;
    // qancha xarajat qilgani
    @Column(name = "price_purchases")
    private Double pricePurchases;
    // nechta kommment yozgani
    @Column(name = "comment_count")
    private Long commentCount;

}
