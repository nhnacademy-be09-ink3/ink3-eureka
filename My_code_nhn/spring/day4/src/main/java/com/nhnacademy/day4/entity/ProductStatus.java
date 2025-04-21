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
@Table(name = "product_status")
public class ProductStatus {

    @Id
    @Column(name = "product_status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productStatusId;

    @Column(name = "status_type")
    private String StatusType;

    public ProductStatus(String statusType) {
        StatusType = statusType;
    }
}
