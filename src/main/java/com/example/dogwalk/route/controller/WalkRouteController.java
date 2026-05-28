package com.example.dogwalk.route.controller;

import com.example.dogwalk.route.dto.RouteSaveRequest;
import com.example.dogwalk.route.service.WalkRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/walks")
public class WalkRouteController {
    private final WalkRouteService walkRouteService;
    // 대량의 경로 데이터를 DTO 객체로 자동 변환하여 한 번에 받아오는 역할
    @PostMapping("/{walkSessionId}/routes")
    public ResponseEntity<String> saveWalkRoutes(
            @PathVariable Long walkSessionId,
            @RequestBody RouteSaveRequest request
    ) {
        walkRouteService.saveRoutes(walkSessionId, request);
        return ResponseEntity.ok("경로 데이터 " + request.getPoints().size() + "개가 성공적으로 저장되었습니다.");
    }
}