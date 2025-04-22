package com.nhnacademy.day4.repository;

import com.nhnacademy.day4.entity.Point;
import com.nhnacademy.day4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Integer> {

    // save - JpaRepository 기본 지원

    List<Point> findPointByUser(User user);
    int countByUser(User user);
}
