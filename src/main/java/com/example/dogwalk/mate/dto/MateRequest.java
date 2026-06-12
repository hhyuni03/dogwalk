package com.example.dogwalk.mate.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MateRequest {
    Long userId;
    Double latitude;
    Double longitude;
}
