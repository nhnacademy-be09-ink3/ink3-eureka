package com.nhnacademy.day4.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "point_type")
public class PointType {

    @Id
    @Column(name = "point_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pointTypeId;

    @Column(name = "point_type_name")
    private String pointTypeName;
}



