package com.example.dogwalk.mate.service;

import com.example.dogwalk.mate.dto.NearbyMateResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.geo.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final StringRedisTemplate redisTemplate;
    private static final String LOCATION_KEY = "walk:locations";
    // 내 좌표 가져오기
    @Transactional
    public Point getUserLocation(Long userId){
        String member = "user:" + userId;
        List<Point> positions = redisTemplate.opsForGeo().position(LOCATION_KEY, member);
        if (positions != null && !positions.isEmpty() && positions.get(0) != null) {
            return positions.get(0);
        }
        throw new IllegalStateException("좌표를 가져오지 못햇습니다.");
    }

    // 내 좌표 update 하기
    @Transactional
    public void updateLocation(Long userId, Double lat, Double lng) {
        String member = "user:" + userId;
        Point point = new Point(lng, lat);
        redisTemplate.opsForGeo().add(LOCATION_KEY, point, member);
    }

    // 근처 반경 1Km 내에 있는 유저 정보 가져오기
    @Transactional
    public List<NearbyMateResponse> findNearbyMates(Long userId) {
        Point myPoint = getUserLocation(userId);
        // 거리 순(오름차순)으로 redis 불러오기
        RedisGeoCommands.GeoSearchCommandArgs args = RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs()
                .includeDistance()
                .sortAscending();
        // 1km 이내인 results 불러오기
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisTemplate.opsForGeo().search(
                LOCATION_KEY,
                GeoReference.<String>fromCoordinate(myPoint),
                new Distance(1.0, Metrics.KILOMETERS),
                args
        );
        List<NearbyMateResponse> nearbyMates = new ArrayList<>();
        if (results != null) {
            for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
                Distance distance = result.getDistance();
                double meterDistance = distance.getValue();

                RedisGeoCommands.GeoLocation<String> location = result.getContent();
                String memberKey = location.getName();
                long nearbyUserId = Long.parseLong(memberKey.replace("user:", ""));

                if(nearbyUserId == userId){
                    continue;
                }
                nearbyMates.add(new NearbyMateResponse(nearbyUserId, meterDistance));
            }
        }
        return nearbyMates;
    }
}
