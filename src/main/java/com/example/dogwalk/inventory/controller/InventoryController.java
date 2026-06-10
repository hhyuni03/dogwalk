package com.example.dogwalk.inventory.controller;

import com.example.dogwalk.inventory.domain.InventoryItem;
import com.example.dogwalk.inventory.dto.InventoryRequest;
import com.example.dogwalk.inventory.dto.InventoryResponse;
import com.example.dogwalk.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("{userId}")
    public ResponseEntity<List<InventoryResponse>> getInventory(@PathVariable Long userId) {
        List<InventoryItem> items = inventoryService.getInventory(userId);
        List<InventoryResponse> response = items.stream()
                .map(InventoryResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{inventoryItemId}/equip")
    public ResponseEntity<String> equipInventory(
            @PathVariable Long inventoryItemId,
            @RequestBody InventoryRequest request
    ) {
        inventoryService.changeEquipState(request.getUserId(), inventoryItemId);
        return ResponseEntity.ok("아이템 장착 상태가 변경되었습니다.");
    }
}
