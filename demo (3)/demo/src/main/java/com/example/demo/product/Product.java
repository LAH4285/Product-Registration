package com.example.demo.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Product {
    // ** pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ** 상품명, 입력값 필수
    @Column(length = 100, nullable = false)
    private String productName;

    // ** 상품설명, 입력값 필수
    @Column(length = 500, nullable = false)
    private String description;

    // ** 상품 이미지 정보
    @Column(length = 100)
    private String image;

    // ** 상품 가격
    @Column(nullable = false)
    private int price;

    // ** 상품 수량
    @Column(nullable = false)
    private int quantity;
}
