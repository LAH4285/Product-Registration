package com.example.demo.product;


import com.example.demo.option.Option;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;


public class ProductResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @AllArgsConstructor
    public static class FindAllDTO{
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

        public FindAllDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
        }

        public Product toEntity(){
            return Product.builder()
                    .id(id)
                    .productName(productName)
                    .description(description)
                    .image(image)
                    .price(price)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class FindByIdDTO{
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
        // **
        private List<OptionDTO> optionList;

        public FindByIdDTO(Product product, List<Option> optionList) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
            this.optionList = optionList.stream().map(OptionDTO::new)
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    public static class OptionDTO{
        private Long id;
        private String optionName;
        private Long Price;
        private Long quantity;

        public OptionDTO(Option option) {
            this.id = option.getId();
            this.optionName = option.getOptionName();
            this.Price = option.getPrice();
            this.quantity = option.getQuantity();
        }
    }
}



