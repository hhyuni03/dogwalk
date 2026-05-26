package com.example.dogwalk.route.service;

import com.example.dogwalk.route.domain.WalkRoute;
import com.example.dogwalk.route.dto.RouteSaveRequest;
import com.example.dogwalk.route.repository.WalkRouteRepository;
import com.example.dogwalk.walk.domain.WalkSession;
import com.example.dogwalk.walk.repository.WalkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalkRouteService {
    private final WalkRepository walkRepository;
    private final WalkRouteRepository walkRouteRepository;

    @Transactional
    public void saveRoutes(Long walkSessionId, RouteSaveRequest request) {
        WalkSession walkSession = walkRepository.findById(walkSessionId)
                .orElseThrow(() -> new IllegalArgumentException("산책 기록이 없습니다."));

        List<WalkRoute> routesToSave = request.getPoints().stream()
                .map(point -> WalkRoute.builder()
                        .walkSession(walkSession)
                        .sequence(point.getSequence())
                        .latitude(point.getLatitude())
                        .longitude(point.getLongitude())
                        .accuracy(point.getAccuracy())
                        .speed(point.getSpeed())
                        .recordedAt(point.getRecordedAt())
                        .build())
                .toList();
        walkRouteRepository.saveAll(routesToSave);
    }
}
