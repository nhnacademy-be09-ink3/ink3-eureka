package com.nhnacademy.day4;

import com.nhnacademy.day4.entity.Product;
import com.nhnacademy.day4.entity.ProductStatus;
import com.nhnacademy.day4.repository.ProductRepository;
import com.nhnacademy.day4.repository.ProductStatusRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductMangerTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductStatusRepository productStatusRepository;

    @Test
    void createProductTest(){
        // given
        int id=  1;
        ProductStatus productStatus = new ProductStatus( "판매중");
        productStatusRepository.save(productStatus);
        Product product = new Product("product", 100, "good", 10, null, LocalDateTime.now(), productStatus);
        productRepository.save(product);

        // when
        Product product2 = productRepository.findById(id).orElse(null);

        // then
        assertThat(product2).isNotNull();
        assertThat(product2.getProductId()).isEqualTo(id);
    }


    @Sql("user-test.sql")
    @Test
    void findProductTest() {
        // given
        int id=  1;

        // when
        Product product = productRepository.findById(id).orElse(null);

        // then
        assertThat(product).isNotNull();
        assertThat(product.getProductId()).isEqualTo(id);
    }
}
