package com.example.dogwalk.shop.service;

import com.example.dogwalk.inventory.domain.InventoryItem;
import com.example.dogwalk.inventory.repository.InventoryItemRepository;
import com.example.dogwalk.item.domain.Item;
import com.example.dogwalk.item.repository.ItemRepository;
import com.example.dogwalk.point.domain.PointTransaction;
import com.example.dogwalk.point.domain.SourceType;
import com.example.dogwalk.point.domain.TransactionType;
import com.example.dogwalk.point.repository.PointTransactionRepository;
import com.example.dogwalk.user.domain.User;
import com.example.dogwalk.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ShopService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final PointTransactionRepository pointTransactionRepository;

    @Transactional
    public void purchaseItem(Long userId, Long itemId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 아이템입니다."));

        // 아이템이 판매중인지 확인
        if (!item.isActive()){
            throw new IllegalStateException("현재 판매중인 아이템이 아닙니다.");
        }

        // 잔액 검사
        if(user.getCurrentPoint()<item.getPrice()){
            throw new IllegalStateException("포인트가 부족하여 구매할 수 없습니다.");
        }

        // 중복 소유 여부
        if (inventoryItemRepository.existsByUserAndItem(user, item)) {
            throw new IllegalStateException("이미 보유하고 있는 아이템입니다.");
        }

        user.minusPoint(item.getPrice());

        InventoryItem inventoryItem = InventoryItem.builder()
                .user(user)
                .item(item)
                .build();
        inventoryItemRepository.save(inventoryItem);

        PointTransaction pointTransaction = PointTransaction.builder()
                .user(user)
                .transactionType(TransactionType.SPEND)
                .sourceType(SourceType.ITEM_PURCHASE)
                .sourceId(item.getId())
                .amount(item.getPrice())
                .build();
        pointTransactionRepository.save(pointTransaction);
    }
}
