package com.example.dogwalk.photo.domain;

import com.example.dogwalk.walk.domain.WalkSession;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="photos")
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)

public class WalkPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walk_session_id", nullable = false)
    private WalkSession walkSession;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private WalkPhotoType type;

    @Column(nullable = false, length = 500)
    private String imageUrl;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lng;

    @Column(nullable = false)
    private boolean metadataVerified;

    @Column(nullable = false)
    private boolean dogDetected;

    @Column(nullable = false)
    private Integer verificationScore;

    private LocalDateTime capturedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Builder
    public WalkPhoto(WalkSession walkSession, WalkPhotoType type, String imageUrl,
                     Double lat, Double lng, LocalDateTime capturedAt,
                     boolean metadataVerified, boolean dogDetected, Integer verificationScore) {
        this.walkSession = walkSession;
        this.type = type;
        this.imageUrl = imageUrl;
        this.lat = lat;
        this.lng = lng;
        this.capturedAt = capturedAt;
        this.metadataVerified = metadataVerified;
        this.dogDetected = dogDetected;
        this.verificationScore = verificationScore;
        this.createdAt = LocalDateTime.now();
    }

}
