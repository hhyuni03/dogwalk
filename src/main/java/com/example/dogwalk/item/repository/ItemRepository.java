package com.example.dogwalk.item.repository;

import com.example.dogwalk.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository <Item, Long> {
}
