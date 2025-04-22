package com.nhnacademy.day4.repository;

import com.nhnacademy.day4.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    // save -  JpaRepository 기본 지원
    // update -  JpaRepository 기본 지원
    // findAll - JpaRepository 기본 지원
    // totalCount - JpaRepository 기본 지원

    Optional<Product> findByProductId(int productId);
    int deleteProductByProductId(int productId);
    int countProductByProductId(int productId);
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product p SET p.productAmount = :productId WHERE p.productId = :amount")
    int updateProductAmountByProductId(int productId, int amount);
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product p SET p.productPrice = :productId WHERE p.productId = :price")
    int updateProductPriceByProductId(int productId, int price);


    //List<Product> productList(int page, int pageSize)
    // Page<Product> findAll(Pageable pageable);
    //Page<Product> findAll(int categoryId,int page, int pageSize)
    //Page<Product> findAll(String searchName, int page, int pageSize)

}
