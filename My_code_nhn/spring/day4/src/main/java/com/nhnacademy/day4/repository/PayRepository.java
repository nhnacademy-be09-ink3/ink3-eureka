package com.nhnacademy.day4.repository;

import com.nhnacademy.day4.entity.Order;
import com.nhnacademy.day4.entity.Pay;
import com.nhnacademy.day4.page.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayRepository extends JpaRepository<Pay, Integer> {

    // save -  JpaRepository 기본 지원
    // update -  JpaRepository 기본 지원
    // delete -  JpaRepository 기본 지원


    Pay findPayByOrder(Order order);
    int countAllByPayId(int payId);
}
