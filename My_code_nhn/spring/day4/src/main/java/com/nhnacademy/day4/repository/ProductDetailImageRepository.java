package com.nhnacademy.day4.repository;

import com.nhnacademy.day4.entity.Product;
import com.nhnacademy.day4.entity.ProductDetailImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDetailImageRepository extends JpaRepository<ProductDetailImage, Integer> {
    // save -  JpaRepository 기본 지원
    // update - JpaRepository 기본 지원

    int deleteProductDetailImageByProduct(Product product);
    List<ProductDetailImage> findProductDetailImagesByProduct(Product product);
}
