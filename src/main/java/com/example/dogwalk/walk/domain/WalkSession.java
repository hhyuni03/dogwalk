package com.example.dogwalk.walk.domain;

import com.example.dogwalk.dog.domain.Dog;
import com.example.dogwalk.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "walk_sessions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalkSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id", nullable = false)
    private Dog dog;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private WalkStatus status;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    private Double startLat;
    private Double startLng;

    private Double endLat;
    private Double endLng;

    private Double totalDistanceMeters;
    private Integer totalDurationSeconds;

    @Column(nullable = false)
    private Integer earnedPoint;

    @Column(nullable = false)
    private Integer mateWalkBonusPoint;

    @Column(length = 500)
    private String startPhotoUrl;

    @Column(length = 500)
    private String endPhotoUrl;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private VerificationStatus verificationStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    // 🌟 산책 '시작(생성)' 시점에 필요한 데이터만 받는 Builder
    @Builder
    public WalkSession(User user, Dog dog, Double startLat, Double startLng) {
        this.user = user;
        this.dog = dog;
        this.status = WalkStatus.WALKING; // 생성과 동시에 산책 시작 상태로!
        this.startedAt = LocalDateTime.now();
        this.startLat = startLat;
        this.startLng = startLng;
        this.earnedPoint = 0;
        this.mateWalkBonusPoint = 0;
        this.verificationStatus = VerificationStatus.NONE;
        this.createdAt = LocalDateTime.now();
    }
}
