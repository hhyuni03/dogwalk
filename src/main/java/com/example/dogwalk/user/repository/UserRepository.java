package com.example.dogwalk.user.repository;

import com.example.dogwalk.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
