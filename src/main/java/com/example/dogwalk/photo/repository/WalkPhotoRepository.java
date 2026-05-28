package com.example.dogwalk.photo.repository;

import com.example.dogwalk.photo.domain.WalkPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WalkPhotoRepository extends JpaRepository<WalkPhoto, Long> {
    // 특정 user의 walkSession에 함께 기록되어 있는 walkPhoto를 내림차순 순으로 가져오기 위한 쿼리
    @Query("""
           SELECT p 
           FROM WalkPhoto p 
           JOIN p.walkSession w 
           WHERE w.user.id = :userId 
           ORDER BY p.createdAt DESC
           """)
    List<WalkPhoto> findGalleryByUserId(@Param("userId") Long userId);
}
