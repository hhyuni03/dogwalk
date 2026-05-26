package com.example.dogwalk.photo.controller;

import com.example.dogwalk.photo.domain.PhotoType;
import com.example.dogwalk.photo.service.WalkPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/walks")

public class WalkPhotoController {
    private final WalkPhotoService walkPhotoService;

    @PostMapping(value = "/{walkSessionId}/photos", consumes = "multipart/form-data")
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
}
