package com.example.demo.product;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDTO {

    // ** pk
    private Long id;
    // ** 상품명, 입력값 필수
    private String productName;
    // ** 상품설명, 입력값 필수
    private String description;
    // ** 상품 이미지 정보
    private String image;
    // ** 상품 가격
    private int price;
    // ** 상품 수량
    private int quantity;


    public ProductDTO(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.image = product.getImage();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
    }
}
