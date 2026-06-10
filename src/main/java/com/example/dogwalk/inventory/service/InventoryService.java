package com.example.dogwalk.inventory.service;

import com.example.dogwalk.inventory.domain.InventoryItem;
import com.example.dogwalk.inventory.repository.InventoryItemRepository;
import com.example.dogwalk.user.domain.User;
import com.example.dogwalk.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryItemRepository inventoryItemRepository;
    private final UserRepository userRepository;

    // 인벤토리 목록 조회
    @Transactional(readOnly = true)
    public List<InventoryItem> getInventory(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return inventoryItemRepository.findByUserIdWithItem(user.getId());
    }

    // 아이템 장착 상태 변경
    @Transactional
    public void changeEquipState(Long userId, Long inventoryItemId){
        InventoryItem item = inventoryItemRepository.findById(inventoryItemId)
                .orElseThrow(()->new IllegalArgumentException("해당 아이템이 인벤토리에 없습니다."));
        if(!item.getUser().getId().equals(userId)){
            throw new IllegalStateException("본인의 아이템만 장착/해제할 수 있습니다.");
        }
        // 아이템을 장착 중이였다면 장착 해제
        if(item.isEquipped()){
            item.unequip();
        } else{
            inventoryItemRepository.findByUserIdAndItemItemTypeAndIsEquippedTrue(item.getUser().getId(), item.getItem().getItemType())
                    .ifPresent(InventoryItem::unequip);
            item.equip();
        }
    }
}
