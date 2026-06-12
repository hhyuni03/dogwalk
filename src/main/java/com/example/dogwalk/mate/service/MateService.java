package com.example.dogwalk.mate.service;

import com.example.dogwalk.mate.domain.Mate;
import com.example.dogwalk.mate.repository.MateRepository;
import com.example.dogwalk.user.domain.User;
import com.example.dogwalk.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MateService {
    private final UserRepository userRepository;
    private final LocationService locationService;
    private final MateRepository mateRepository;

    @Transactional
    public void sendMate(Long requesterId, Long targetId){
        User requester = userRepository.findById(requesterId).orElse(null);
        User target = userRepository.findById(targetId).orElse(null);

        Point myLocation = locationService.getUserLocation(requesterId);

        Mate mate = Mate.builder()
                .requester(requester)
                .target(target)
                .requestLng(myLocation.getX())
                .requestLat(myLocation.getY())
                .build();

        mateRepository.save(mate);
    }
}
