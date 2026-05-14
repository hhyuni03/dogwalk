package com.example.dogwalk.dog.domain;

import com.example.dogwalk.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name="dogs")
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(length=255)
    private String baseImageUrl;

    @Column(length=255)
    private String aiCharacterImageUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Dog(User user, String name, Integer age, String baseImageUrl, String aiCharacterImageUrl){
        this.user = user;
        this.name = name;
        this.age = age;
        this.baseImageUrl = baseImageUrl;
        this.aiCharacterImageUrl = aiCharacterImageUrl;
        this.createdAt = LocalDateTime.now();
    }
}
