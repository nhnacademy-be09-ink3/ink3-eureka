package com.nhnacademy.day4.repository;

import com.nhnacademy.day4.entity.Order;
import com.nhnacademy.day4.entity.OrderItem;
import com.nhnacademy.day4.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    // save -  JpaRepository 기본 지원
    // update -  JpaRepository 기본 지원
    // findById -  JpaRepository 기본 지원
    // delete -  JpaRepository 기본 지원

    int countAllByOrderAndProduct(Order order, Product product);
}
