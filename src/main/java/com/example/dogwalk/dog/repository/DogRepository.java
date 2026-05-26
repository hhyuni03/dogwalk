package com.example.dogwalk.dog.repository;

import com.example.dogwalk.dog.domain.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository <Dog, Long> {
}
