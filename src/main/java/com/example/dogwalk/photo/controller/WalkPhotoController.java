package com.example.dogwalk.photo.controller;

import com.example.dogwalk.photo.domain.PhotoType;
import com.example.dogwalk.photo.dto.GalleryPhotoResponse;
import com.example.dogwalk.photo.service.WalkPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")

public class WalkPhotoController {
    private final WalkPhotoService walkPhotoService;

    @PostMapping(value = "/walks/{walkSessionId}/photos", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadWalkPhoto(
            @PathVariable long walkSessionId,
            @RequestParam("image") MultipartFile image,
            @RequestParam("lat") Double lat,
            @RequestParam("lng") Double lng,
            @RequestParam("type") PhotoType type
    ) throws IOException {
        walkPhotoService.uploadPhoto(walkSessionId, image, lat, lng, type);
        return ResponseEntity.ok("사진이 성공적을 저장되었습니다.");
    }

    @GetMapping("/users/{userId}/photos")
    public ResponseEntity<List<GalleryPhotoResponse>> getUserGallery(@PathVariable long userId) {
        List <GalleryPhotoResponse> gallery = walkPhotoService.getUserGallery(userId);
        return ResponseEntity.ok(gallery);
    }
}
