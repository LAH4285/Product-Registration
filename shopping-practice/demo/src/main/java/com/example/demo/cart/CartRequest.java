package com.example.demo.cart;

import com.example.demo.User.User;
import com.example.demo.option.Option;
import lombok.Getter;
import lombok.Setter;

public class CartRequest {

    @Getter
    @Setter
    public static class SaveDTO{
        private Long optionId;
        private Long quantity;

        public Cart toEntity(Option option, User user){
            return Cart.builder()
                    .option(option)
                    .user(user)
                    .quantity(quantity)
                    .price(option.getPrice() * quantity)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class UpdateDTO{
        private Long cartId;
        private Long quantity;
    }
}
