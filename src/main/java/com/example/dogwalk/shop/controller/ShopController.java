package com.example.dogwalk.shop.controller;

import com.example.dogwalk.shop.dto.ShopRequest;
import com.example.dogwalk.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/shop")
public class ShopController {
    private final ShopService shopService;

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseItem(@RequestBody ShopRequest request) {
        shopService.purchaseItem(request.getUserId(), request.getItemId());
        return ResponseEntity.ok("아이템 구매가 완료되었습니다.");
    }
}
