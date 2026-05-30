package com.example.dogwalk.point.domain;

import com.example.dogwalk.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name ="point_transactions")
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class PointTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private SourceType sourceType;

    private Long sourceId;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public PointTransaction(User user, TransactionType transactionType, SourceType sourceType, Long sourceId, int amount) {
        this.user = user;
        this.transactionType = transactionType;
        this.sourceType = sourceType;
        this.sourceId = sourceId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }
}
