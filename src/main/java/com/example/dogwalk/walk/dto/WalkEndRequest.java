package com.example.dogwalk.walk.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WalkEndRequest {
    private Double endLat;
    private Double endLng;
    private Double totalDistanceMeters;
    private boolean isPhotoVerified;
    private String endPhotoUrl;
}
