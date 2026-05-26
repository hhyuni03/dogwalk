package com.example.dogwalk.walk.repository;

import com.example.dogwalk.walk.domain.WalkSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalkRepository extends JpaRepository<WalkSession, Long> {
}