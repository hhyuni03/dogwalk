package com.example.dogwalk.inventory.domain;

import com.example.dogwalk.item.domain.Item;
import com.example.dogwalk.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id", nullable=false)
    private Item item;

    @Column(nullable = false)
    private boolean isEquipped;

    @Column(nullable = false, updatable = false)
    private LocalDateTime ownedAt;

    @Builder
    public InventoryItem(User user, Item item, boolean isEquipped, LocalDateTime ownedAt) {
        this.user = user;
        this.item = item;
        this.isEquipped = false;
        this.ownedAt = LocalDateTime.now();
    }

    public void equip() {
        this.isEquipped = true;
    }
    public void unequip() {
        this.isEquipped = false;
    }
}
