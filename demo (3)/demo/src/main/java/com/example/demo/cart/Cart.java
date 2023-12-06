package com.example.demo.cart;

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

    @Column(nullable = false)
    private Long Quantity;

    @Column(nullable = false)
    private Long price;

    @Builder
    public Cart(Long id, Option option, Long quantity, Long price) {
        this.id = id;
        this.option = option;
        Quantity = quantity;
        this.price = price;
    }
}
