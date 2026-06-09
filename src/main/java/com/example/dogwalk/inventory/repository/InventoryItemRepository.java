package com.example.dogwalk.inventory.repository;

import com.example.dogwalk.inventory.domain.InventoryItem;
import com.example.dogwalk.item.domain.Item;
import com.example.dogwalk.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    boolean existsByUserAndItem(User user, Item item);
}