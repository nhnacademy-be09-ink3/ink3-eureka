package com.nhnacademy.day4.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "OrderItem")
public class OrderItem {

    @EmbeddedId
    private OrderItemPK pk;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;


    @Column(name = "amount")
    private int amount;
}
