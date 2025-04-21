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
@Table(name = "product_detail_image")
public class ProductDetailImage {

    @Id
    @Column(name = "detail_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int detailImageId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "image_path")
    private String imagePath;

}
