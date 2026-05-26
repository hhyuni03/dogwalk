package com.example.dogwalk.user.domain;

import com.example.dogwalk.dog.domain.Dog;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name="users")
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 15)
    private String nickname;

    @Column(length = 255)
    private String profileImageUrl;

    @Column(nullable = false)
    private Integer currentPoint;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private Integer exp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dog> dogs = new ArrayList<>();


    @Builder
    public User(String email, String nickname, String profileImageUrl) {
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.currentPoint = 0;
        this.level = 1;
        this.exp = 0;
        this.status = UserStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    // 포인트 추가
    public void addPoint(Integer point) {
        this.currentPoint += point;
    }
    // 경험치 추가
    public void addExp(Integer earnedExp) {
        this.exp += earnedExp;
        checkLevelUp();
    }
    // 레벨업 계산 로직
    private void checkLevelUp() {
        // 나중에 수학적 기준 세우기!
        int requiredExp = this.level * 1000;
        // exp가 레벨업 가능 경험치까지 도달하면, level 갱신
        while (this.exp>=requiredExp) {
            this.exp -= requiredExp;
            this.level++;
            requiredExp = this.level * 1000;
        }
    }
}
