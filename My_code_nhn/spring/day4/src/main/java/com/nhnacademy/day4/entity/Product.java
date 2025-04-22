package com.nhnacademy.day4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @NotNull
    @Column(name = "product_name")
    private String productName;

    @NotNull
    @Column(name = "product_price")
    private int productPrice;

    @Column(name = "product_details")
    private String productDetails;

    @Column(name = "product_amount")
    private int productAmount;

    @Column(name = "product_preview_image")
    private String productPreviewImage;

    @Column(name = "product_create_at")
    private LocalDateTime productCreateAt;

    @ManyToOne
    @JoinColumn(name = "product_status_id")
    private ProductStatus productStatus;

    public Product(String productName, int productPrice, String productDetails,
                   int productAmount, String productPreviewImage,
                   LocalDateTime productCreateAt, ProductStatus productStatus) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDetails = productDetails;
        this.productAmount = productAmount;
        this.productPreviewImage = productPreviewImage;
        this.productCreateAt = productCreateAt;
        this.productStatus = productStatus;
    }
}
