package com.nhnacademy.day4.repository;


import com.nhnacademy.day4.entity.Category;
import com.nhnacademy.day4.entity.Product;
import com.nhnacademy.day4.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    // save -  JpaRepository 기본 지원
    // update -  JpaRepository 기본 지원
    // findById -  JpaRepository 기본 지원
    // delete -  JpaRepository 기본 지원
    // findAll -  JpaRepository 기본 지원
    // count - JpaRepository 기본 지원

    @Modifying(clearAutomatically = true)
    @Query("SELECT pc.product FROM ProductCategory pc WHERE pc.category.categoryId = :categoryId")
    List<Category> findProductsByCategoryId(int categoryId);
    @Modifying(clearAutomatically = true)
    @Query("SELECT pc.category FROM ProductCategory pc WHERE pc.product.productId = :productId")
    List<Product> findProductsByProductId(int productId);
    List<ProductCategory> findProductCategoriesByProduct(Product product);

}
