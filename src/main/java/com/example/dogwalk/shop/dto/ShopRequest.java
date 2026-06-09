package com.example.dogwalk.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShopRequest {
    private Long userId;
    private Long itemId;
}
