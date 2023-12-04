package com.example.demo.option;

import com.example.demo.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Option_tb")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    // ** 옵션상품 이름, 필수 입력값
    @Column(length = 100, nullable = false)
    private String optionName;

    // ** 옵션상품 가격
    private Long Price;

    // ** 옵션상품 수량
    private Long quantity;
}
