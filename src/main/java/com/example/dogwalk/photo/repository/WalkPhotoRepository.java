package com.example.dogwalk.photo.repository;

import com.example.dogwalk.photo.domain.WalkPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalkPhotoRepository extends JpaRepository<WalkPhoto, Long> {
}
