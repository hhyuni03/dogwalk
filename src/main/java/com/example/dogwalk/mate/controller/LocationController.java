package com.example.dogwalk.mate.controller;

import com.example.dogwalk.mate.domain.Mate;
import com.example.dogwalk.mate.dto.MateRequest;
import com.example.dogwalk.mate.dto.NearbyMateResponse;
import com.example.dogwalk.mate.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/location")
public class LocationController {
    private final LocationService locationService;

    @PostMapping("/update")
    public ResponseEntity<Void> update(@RequestBody MateRequest request) {
        locationService.updateLocation(request.getUserId(), request.getLatitude(), request.getLongitude());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/nearby/{userId}")
    public ResponseEntity<List<NearbyMateResponse>> nearby(@PathVariable Long userId) {
        List<NearbyMateResponse> response = locationService.findNearbyMates(userId);
        return ResponseEntity.ok(response);
    }
}
