package com.example.dogwalk.route.controller;

import com.example.dogwalk.route.dto.RouteSaveRequest;
import com.example.dogwalk.route.service.WalkRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/walks") // 주소는 일관성 있게 유지합니다.
public class WalkRouteController {

    private final WalkRouteService walkRouteService;

    // 대량의 경로 데이터를 한 번에 받는 창구입니다.
    @PostMapping("/{walkSessionId}/routes")
    public ResponseEntity<String> saveWalkRoutes(
            @PathVariable Long walkSessionId,
            @RequestBody RouteSaveRequest request
    ) {
        // 경로 전용 주방장에게 일거리를 넘깁니다.
        walkRouteService.saveRoutes(walkSessionId, request);

        // 몇 개의 점이 저장되었는지 세어서 친절하게 응답해 줍니다.
        return ResponseEntity.ok("경로 데이터 " + request.getPoints().size() + "개가 성공적으로 저장되었습니다.");
    }
}