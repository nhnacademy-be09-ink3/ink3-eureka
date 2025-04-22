package com.nhnacademy.day4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "point_type_name")
    private String pointTypeName;
}



