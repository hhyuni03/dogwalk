package com.example.dogwalk.mate.controller;

import com.example.dogwalk.mate.service.MateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mate")
public class MateController {
    private final MateService mateService;
    @PostMapping("/send/{targetId}")
    public ResponseEntity<Void> sendMate(
            @RequestParam Long requesterId,
            @PathVariable Long targetId
    ){
        mateService.sendMate(requesterId, targetId);
        return ResponseEntity.ok().build();
    }
}
