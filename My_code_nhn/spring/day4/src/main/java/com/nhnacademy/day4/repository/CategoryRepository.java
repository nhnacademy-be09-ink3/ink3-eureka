package com.nhnacademy.day4.repository;

import com.nhnacademy.day4.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // save -  JpaRepository 기본 지원
    // update -  JpaRepository 기본 지원
    // findById -  JpaRepository 기본 지원
    // delete -  JpaRepository 기본 지원
    // findAll -  JpaRepository 기본 지원
    // count - JpaRepository 기본 지원

}
