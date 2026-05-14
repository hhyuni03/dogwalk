package com.example.dogwalk.walk.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WalkStartRequest {
    private Long userId;
    private Long dogId;
    private Double startLat;
    private Double startLng;
}
