package com.example.dogwalk.route.repository;

import com.example.dogwalk.route.domain.WalkRoute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalkRouteRepository extends JpaRepository<WalkRoute, Long> {
}
