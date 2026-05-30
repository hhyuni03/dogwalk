package com.example.dogwalk.item.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 💡 PROTECTED로 수정 완료!
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemType itemType;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int rarity;

    private String imageUrl;

    @Column(nullable = false)
    private boolean isActive = true;

    @Builder
    public Item(String name, ItemType itemType, int price, int rarity, String imageUrl) {
        this.name = name;
        this.itemType = itemType;
        this.price = price;
        this.rarity = rarity;
        this.imageUrl = imageUrl;
    }
}