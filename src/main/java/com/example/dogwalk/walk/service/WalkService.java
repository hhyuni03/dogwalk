package com.example.dogwalk.walk.service;

import com.example.dogwalk.user.domain.User;
import com.example.dogwalk.walk.domain.WalkSession;
import com.example.dogwalk.walk.dto.WalkEndRequest;
import com.example.dogwalk.walk.repository.WalkRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalkService {
    private final WalkRepository walkRepository;
    @Transactional
    public void endWalk(Long walkSessionId, WalkEndRequest request){
        WalkSession walkSession = walkRepository.findById(walkSessionId)
                .orElseThrow(()-> new IllegalArgumentException("산책 기록이 없습니다."));
        User user = walkSession.getUser();

        walkSession.endWalk(request.getEndLat(), request.getEndLng(), request.getTotalDistanceMeters());

        int photoExp=0;
        if(request.isPhotoVerified()){
            walkSession.verifyPhoto(request.getEndPhotoUrl());
            photoExp =100;
        }

        int walkMinutes = walkSession.getTotalDurationSeconds() / 60;
        int totalExp = (walkMinutes*10) + photoExp;

        user.addExp(totalExp);
        user.addPoint(walkSession.getEarnedPoint());
    }
}
