package com.example.dogwalk.photo.dto;

import com.example.dogwalk.photo.domain.WalkPhoto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GalleryPhotoResponse {
    // 세 개의 요소만 가볍게..
    private Long photoId;
    private String imageUrl;
    private LocalDateTime capturedAt;

    public GalleryPhotoResponse(WalkPhoto photo) {
        this.photoId = photo.getId();
        this.imageUrl = photo.getImageUrl();
        this.capturedAt = photo.getCapturedAt();
    }
}
