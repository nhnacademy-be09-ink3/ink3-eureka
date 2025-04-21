package com.nhnacademy.day4.repository;

import com.nhnacademy.day4.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
