package com.example.demo.product;

import com.example.demo.option.Option;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private int price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Option>  option =  new ArrayList<>();

    @Builder
    public Product(Long id, String productName, String description, String image, int price, List<Option> option) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
        this.option = option;
    }

    public void updateFromDTO(ProductResponse.FindAllDTO productDTO){

        this.productName = productDTO.getProductName();
        this.description = productDTO.getDescription();
        this.image = productDTO.getImage();
        this.price = productDTO.getPrice();
    }
}
