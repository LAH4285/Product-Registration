package com.example.demo.cart;

import com.example.demo.User.User;
import com.example.demo.option.Option;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "cart_tb",
        indexes = {
                @Index(name = "cart_user_id_index", columnList = "user_id"),
                @Index(name = "cart_option_id_index", columnList = "option_id"),
        },
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_cart_option_user", columnNames = {"user_id", "option_id"})
        })
public class Cart {
    // ** PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Option option;

    // ** user별로 카트에 묶임
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    // ** 수량
    @Column(nullable = false)
    private Long Quantity;
    // ** 가격
    @Column(nullable = false)
    private Long price;

    @Builder
    public Cart(Long id, Option option, User user, Long quantity, Long price) {
        this.id = id;
        this.option = option;
        this.user = user;
        this.Quantity = quantity;
        this.price = price;
    }

    public void update(Long quantity, Long price){
        this.Quantity = quantity;
        this.price = price;
    }
}
