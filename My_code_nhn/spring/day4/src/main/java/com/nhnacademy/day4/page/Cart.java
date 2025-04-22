package com.nhnacademy.day4.page;

import com.nhnacademy.day4.entity.Product;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class Cart {
    private List<Goods> productList;

    public Cart() {
        this.productList = new ArrayList<>();
    }

    public List<Goods> getProductList() {
        Collections.sort(productList);
        return productList;
    }
    public void setProductList(List<Goods> productList) {
        Collections.sort(productList);
        this.productList = productList;
    }

    public List<Goods> updateProductList(int id, int amount){
        for(int i=0;i<productList.size();i++){
            Goods g = productList.get(i);
            if(g.product.getProductId()==id){
               productList.remove(i);
                g.amount = amount;
                productList.add(g);
                return productList;
           }
        }
        Collections.sort(productList);
        return productList;
    }

    public List<Goods> removeProduct(int id){
        for(int i=0;i<productList.size();i++){
            Goods g = productList.get(i);
            if(g.product.getProductId()==id){
                productList.remove(i);
                return productList;
            }
        }
        Collections.sort(productList);
        return productList;
    }


    public static class Goods implements Comparable<Goods> {
        private Product product;
        private int amount;

        public Goods(Product product, int amount) {
            this.amount = amount;
            this.product = product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public Product getProduct() { // 추가
            return product;
        }

        public int getAmount() { // 추가
            return amount;
        }
        @Override
        public int compareTo(Goods o) {
            return this.product.getProductId() - o.product.getProductId();
        }
    }
}