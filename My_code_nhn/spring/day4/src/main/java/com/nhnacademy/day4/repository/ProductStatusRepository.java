package com.nhnacademy.day4.repository;

import com.nhnacademy.day4.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStatusRepository extends JpaRepository<ProductStatus, Integer> {
}
