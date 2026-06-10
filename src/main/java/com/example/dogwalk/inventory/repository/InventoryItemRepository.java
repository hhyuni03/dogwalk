package com.example.dogwalk.inventory.repository;

import com.example.dogwalk.inventory.domain.InventoryItem;
import com.example.dogwalk.item.domain.Item;
import com.example.dogwalk.item.domain.ItemType;
import com.example.dogwalk.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    boolean existsByUserAndItem(User user, Item item);

    @Query("select i from InventoryItem i join fetch i.item where i.user.id = :userId")
    List<InventoryItem> findByUserIdWithItem(@Param("userId") Long userId);
    Optional<InventoryItem> findByUserIdAndItemItemTypeAndIsEquippedTrue(Long userId, ItemType itemType);
}