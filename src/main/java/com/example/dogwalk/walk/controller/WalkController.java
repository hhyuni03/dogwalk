package com.example.dogwalk.walk.controller;

import com.example.dogwalk.walk.dto.WalkEndRequest;
import com.example.dogwalk.walk.dto.WalkStartRequest;
import com.example.dogwalk.walk.service.WalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/walks")
public class WalkController {
    private final WalkService walkService;

    //산책 시작
    @PostMapping("/start")
    public ResponseEntity<Long> startWalk(@RequestBody WalkStartRequest request) {
        Long newWalkSessionId = walkService.startWalk(request);
        return ResponseEntity.ok(newWalkSessionId);
    }
    // 산책 종료 및 보상 받기
    @PostMapping("/{walkSessionId}/end")
    public ResponseEntity<String> endWalk(
            @PathVariable Long walkSessionId,
            @RequestBody WalkEndRequest request){
        walkService.endWalk(walkSessionId, request);

        return ResponseEntity.ok("산책이 성공적으로 종료되고 보상이 지급 되었습니다.");
    }
}
