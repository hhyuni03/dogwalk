package com.example.dogwalk.inventory.dto;

import com.example.dogwalk.inventory.domain.InventoryItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InventoryResponse {
    private Long inventoryItemId;
    private String itemName;
    private String itemType;
    private boolean isEquipped;

    public static InventoryResponse from(InventoryItem entity) {
        return InventoryResponse.builder()
                .inventoryItemId(entity.getId())
                .itemName(entity.getItem().getName())
                .itemType(entity.getItem().getItemType().name())
                .isEquipped(entity.isEquipped())
                .build();
    }
}