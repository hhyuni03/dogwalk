package com.example.dogwalk.mate.repository;

import com.example.dogwalk.mate.domain.Mate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MateRepository extends JpaRepository<Mate, Long> {
}
