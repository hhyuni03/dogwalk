package com.example.dogwalk.photo.service;

import com.example.dogwalk.photo.domain.WalkPhoto;
import com.example.dogwalk.photo.dto.GalleryPhotoResponse;
import com.example.dogwalk.photo.repository.WalkPhotoRepository;
import com.example.dogwalk.walk.domain.WalkSession;
import com.example.dogwalk.walk.repository.WalkRepository;
import com.example.dogwalk.photo.domain.PhotoType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalkPhotoService {
    private final WalkRepository walkRepository;
    private final WalkPhotoRepository walkPhotoRepository;

    @Value("${file.dir}")
    private String fileDir;

    @Transactional
    public Long uploadPhoto(Long walkSessionId, MultipartFile image, Double lat, Double lng, PhotoType type) throws IOException {
        WalkSession walkSession = walkRepository.findById(walkSessionId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 산책 기록이 없습니다."));
        // 파일 이름 암호화
        String originalFilename = image.getOriginalFilename();
        String savedFilename = UUID.randomUUID().toString() + "_" + originalFilename;
        // 컴퓨터에 저장
        String fullPath = fileDir + savedFilename;
        image.transferTo(new File(fullPath));

        // AI 로직으로 교체해야 하는 부분
        boolean isDogDetected = true;
        int score = 100;

        String imageUrl = "/images/" + savedFilename;
        WalkPhoto newPhoto = WalkPhoto.builder()
                .walkSession(walkSession)
                .type(type)
                .imageUrl(imageUrl)
                .lat(lat)
                .lng(lng)
                .capturedAt(LocalDateTime.now())
                .metadataVerified(true)
                .dogDetected(isDogDetected)
                .verificationScore(score)
                .build();

        WalkPhoto savedPhoto = walkPhotoRepository.save(newPhoto);
        return savedPhoto.getId();
    }

    public List<GalleryPhotoResponse> getUserGallery(Long userId){
        List <WalkPhoto> photos = walkPhotoRepository.findGalleryByUserId(userId);
        return photos.stream()
                .map(GalleryPhotoResponse::new)
                .toList();
    }

}
