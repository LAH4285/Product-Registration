package com.example.demo.cart;

import com.example.demo.option.Option;
import com.example.demo.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
// ** 디자인패턴과 유사한 방식/ 교차된 연결/ 브릿지패턴과 비슷한 방식
public class CartResponse {

    @Getter
    @Setter
    public static class UpdateDTO{
        private List<CartDTO> dtoList;

        private Long totalPrice;

        public UpdateDTO(List<Cart> dtoList) {
            this.dtoList = dtoList.stream().map(CartDTO::new).collect(Collectors.toList());
            this.totalPrice = totalPrice;
        }

        @Getter
        @Setter
        public class CartDTO{
            private Long cartId;

            private Long optionId;

            private String optionName;

            private Long Quantity;

            private Long price;

            public CartDTO(Cart cart) {
                this.cartId = cart.getId();
                this.optionId = cart.getOption().getId();
                this.optionName = cart.getOption().getOptionName();
                this.Quantity = cart.getQuantity();
                this.price = cart.getPrice();
            }
        }
    }

    @Getter
    @Setter
    public static class FindAllDTO {
        List<ProductDTO> products;

        private Long totalprice;

        // 람다식은 for문이 생략된 형식이라 생각하면 됨, 간소화하기 위해 사용
        public FindAllDTO(List<Cart> cartList) {
            this.products = products = cartList.stream()
                    .map(cart -> cart.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(cartList, product)).collect(Collectors.toList());

            this.totalprice = cartList.stream()
                    .mapToLong(cart -> cart.getOption().getPrice() * cart.getQuantity())
                    .sum();
        }

        @Getter
        @Setter
        public class ProductDTO {
            private Long id;

            private String productName;

            List<CartDTO> cartDTOs;

            public ProductDTO(List<Cart> cartList, Product product) {
                this.id = product.getId();
                this.productName = product.getProductName();
                this.cartDTOs = cartList.stream()
                        .filter(cart -> cart.getOption().getProduct().getId() == product.getId())
                        .map(CartDTO::new).collect(Collectors.toList());
            }
        }

        @Getter
        @Setter
        public class CartDTO {
            private Long id;

            private OptionDTO optionDTO;

            private Long price;

            private Long quantity;

            public CartDTO(Cart cart) {
                this.id = cart.getId();
                this.optionDTO = new OptionDTO(cart.getOption());
                this.price = cart.getPrice();
                this.quantity = cart.getQuantity();
            }

            @Setter
            @Getter
            public class OptionDTO{
                private Long id;

                private String optionName;

                private Long price;

                public OptionDTO(Option option) {
                    this.id = option.getId();
                    this.optionName = option.getOptionName();
                    this.price = option.getPrice();
                }
            }

        }
    }

}
