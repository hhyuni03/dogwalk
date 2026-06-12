package com.example.dogwalk.mate.domain;

import com.example.dogwalk.user.domain.User;
import com.example.dogwalk.walk.domain.WalkSession;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "mates") // 또는 mates
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id", nullable = false)
    private User target;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walk_session_id")
    private WalkSession walkSession;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MateStatus status;

    private Double requestLat;
    private Double requestLng;

    private LocalDateTime requestedAt;
    private LocalDateTime respondedAt;

    @Builder
    public Mate(User requester, User target, WalkSession walkSession, MateStatus status, Double requestLat, Double requestLng) {
        this.requester = requester;
        this.target = target;
        this.walkSession = walkSession;
        this.status = status;
        this.requestLat = requestLat;
        this.requestLng = requestLng;
        this.requestedAt = LocalDateTime.now();
    }

    public void accept() {
        if (this.status != MateStatus.PENDING) {
            throw new IllegalStateException("대기 중인 요청만 수락할 수 있습니다.");
        }
        this.status = MateStatus.ACCEPTED;
        this.respondedAt = LocalDateTime.now();
    }

    public void reject() {
        if (this.status != MateStatus.PENDING) {
            throw new IllegalStateException("대기 중인 요청만 거절할 수 있습니다.");
        }
        this.status = MateStatus.REJECTED;
        this.respondedAt = LocalDateTime.now();
    }
}