package com.example.dogwalk.route.domain;

import com.example.dogwalk.walk.domain.WalkSession;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "walk_routes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalkRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walk_session_id", nullable = false)
    private WalkSession walkSession;

    @Column(nullable = false)
    private Integer sequence; // 찍힌 순서

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    // GPS 정확도
    private Double accuracy;

    private Double speed;

    @Column(nullable = false, updatable = false)
    private LocalDateTime recordedAt;

    @Builder
    public WalkRoute(WalkSession walkSession, Integer sequence, Double latitude, Double longitude, Double accuracy, Double speed, LocalDateTime recordedAt) {
        this.walkSession = walkSession;
        this.sequence = sequence;
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
        this.speed = speed;
        this.recordedAt = recordedAt;
    }
}