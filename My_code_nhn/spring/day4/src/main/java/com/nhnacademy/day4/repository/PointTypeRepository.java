package com.nhnacademy.day4.repository;

import com.nhnacademy.day4.entity.PointType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTypeRepository extends JpaRepository<PointType, Integer> {
}
