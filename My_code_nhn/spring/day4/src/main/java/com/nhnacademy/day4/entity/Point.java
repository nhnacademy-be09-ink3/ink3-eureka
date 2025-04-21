package com.nhnacademy.day4.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "point")
public class Point {

    @Id
    @Column(name = "point_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pointId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "point_price")
    private int point_price;

    @ManyToOne
    @JoinColumn(name = "point_type_id")
    private PointType pointType;
}
