package org.example.repositories;

import org.example.entities.CategoryEntity;
import org.example.entities.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<GoodsEntity, Integer> {
}
