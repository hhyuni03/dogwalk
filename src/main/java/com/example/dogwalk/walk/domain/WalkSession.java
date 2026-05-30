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

    @Column(length = 500)
    private String endPhotoUrl;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private VerificationStatus verificationStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public WalkSession(User user, Dog dog, Double startLat, Double startLng, LocalDateTime startedAt, WalkStatus status) {
        this.user = user;
        this.dog = dog;
        this.status = WalkStatus.WALKING;
        this.startedAt = LocalDateTime.now();
        this.endedAt = LocalDateTime.now();
        this.startLat = startLat;
        this.startLng = startLng;
        this.earnedPoint = 0;
        this.verificationStatus = VerificationStatus.NONE;
        this.createdAt = LocalDateTime.now();
    }

    //산책 완료 후 기록
    public void endWalk(Double endLat, Double endLng, Double totalDistanceMeters) {
        this.endLat = endLat;
        this.endLng = endLng;
        this.endedAt = LocalDateTime.now();
        this.status = WalkStatus.COMPLETED;
        this.totalDistanceMeters = totalDistanceMeters;
        long seconds = java.time.Duration.between(this.startedAt, this.endedAt).getSeconds();
        this.totalDurationSeconds = (int) seconds;
        int minutes = this.totalDurationSeconds/60;
        this.earnedPoint = minutes/20;
    }
    // 사진 인증이 성공햇을 때 부를 메서드
    public void verifyPhoto(String endPhotoUrl) {
        this.endPhotoUrl = endPhotoUrl;
        this.verificationStatus = VerificationStatus.SUCCESS;
    }
}
