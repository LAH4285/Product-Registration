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
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Option option;

    // ** user별로 카트에 묶임
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private Long Quantity;

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
