package com.example.dogwalk.walk.service;

import com.example.dogwalk.point.domain.PointTransaction;
import com.example.dogwalk.point.domain.SourceType;
import com.example.dogwalk.point.domain.TransactionType;
import com.example.dogwalk.point.repository.PointTransactionRepository;
import com.example.dogwalk.user.domain.User;
import com.example.dogwalk.user.repository.UserRepository;
import com.example.dogwalk.dog.domain.Dog;
import com.example.dogwalk.dog.repository.DogRepository;
import com.example.dogwalk.walk.domain.WalkSession;
import com.example.dogwalk.walk.domain.WalkStatus;
import com.example.dogwalk.walk.domain.VerificationStatus;
import com.example.dogwalk.walk.dto.WalkEndRequest;
import com.example.dogwalk.walk.dto.WalkStartRequest;
import com.example.dogwalk.walk.repository.WalkRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WalkService {
    private final WalkRepository walkRepository;
    private final UserRepository userRepository;
    private final DogRepository dogRepository;
    private final PointTransactionRepository pointTransactionRepository;

    @Transactional
    public Long startWalk(WalkStartRequest request) {
        // 누가 어떤 강아지와 걷는가에 대한 정보
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Dog dog = dogRepository.findById(request.getDogId())
                .orElseThrow(()-> new UnsupportedOperationException("존재하지 않는 강아지입니다."));

        WalkSession newSession = WalkSession.builder()
                .user(user)
                .dog(dog)
                .startedAt(LocalDateTime.now())
                .startLat(request.getStartLat())
                .startLng(request.getStartLng())
                .status(WalkStatus.WALKING)
                .build();

        WalkSession savedSession = walkRepository.save(newSession);
        return savedSession.getId();
    }

    @Transactional
    public void endWalk(Long walkSessionId, WalkEndRequest request){
        WalkSession walkSession = walkRepository.findById(walkSessionId)
                .orElseThrow(()-> new IllegalArgumentException("산책 기록이 없습니다."));
        if (walkSession.getStatus() == WalkStatus.COMPLETED){
            throw new IllegalStateException("이미 종료된 산책입니다.");
        }
        User user = walkSession.getUser();
        walkSession.endWalk(request.getEndLat(), request.getEndLng(), request.getTotalDistanceMeters());

        // 경경치 획득
        int walkMinutes = walkSession.getTotalDurationSeconds() / 60;
        Double walkMeters = walkSession.getTotalDistanceMeters();
        int minExp = walkMinutes * 5; // 시간 보상
        int meterExp = (int)(walkMeters/100) * 10; // 거리 보상
        int photoExp = verifyPhotoAndGetExp(walkSession, request.getEndPhotoUrl()); // 사진 보상
        int totalExp = minExp + meterExp + photoExp;
        user.addExp(totalExp);

        // 포인트 획득
        user.addPoint(walkSession.getEarnedPoint());
        PointTransaction pointTransaction = PointTransaction.builder()
                .user(user)
                .transactionType(TransactionType.EARN)
                .sourceType(SourceType.WALK_DISTANCE)
                .amount(walkSession.getEarnedPoint())
                .build();
        pointTransactionRepository.save(pointTransaction);
    }

    // 산책 인증 사진이 유효한지 판단
    private int verifyPhotoAndGetExp(WalkSession walkSession, String photoUrl){
        if(photoUrl == null || photoUrl.trim().isEmpty()){
            walkSession.updateVerification(VerificationStatus.NONE, 0);
            return 0;
        }

        int randomScore = (int)(Math.random() * 101);

        if (randomScore>=80) {
            walkSession.updateVerification(VerificationStatus.SUCCESS, randomScore);
            return 100;
        }
        else {
            walkSession.updateVerification(VerificationStatus.FAILED, randomScore);
            return 0;
        }
    }
}