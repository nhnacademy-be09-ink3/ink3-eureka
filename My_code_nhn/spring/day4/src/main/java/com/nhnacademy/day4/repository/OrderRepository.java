package com.nhnacademy.day4.repository;

import com.nhnacademy.day4.entity.Order;
import com.nhnacademy.day4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    // save -  JpaRepository 기본 지원
    // update -  JpaRepository 기본 지원
    // findById -  JpaRepository 기본 지원


    int deleteOrderByOrderId(int orderId);
    int countAllByOrderId(int orderId);
    List<Order> findOrdersByUser(User user);
    int countAllByUser(User user);
}
