package com.example.dogwalk.route.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class RouteSaveRequest {
    private List<RoutePoint> points;
    @Getter
    @NoArgsConstructor
    public static class RoutePoint {
        private Integer sequence;
        private Double latitude;
        private Double longitude;
        private Double accuracy;
        private Double speed;
        private LocalDateTime recordedAt;
    }
}