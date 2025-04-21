package com.nhnacademy.day4.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "pay")
public class Pay {

    @Id
    @Column(name = "pay_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int payId;

    @Column(name = "total_price")
    private int totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "pay_time")
    private LocalDateTime payTime;
}
