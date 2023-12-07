package com.example.demo.order.item;


import com.example.demo.option.Option;
import com.example.demo.order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "item_tb",
        indexes = {
                @Index(name = "item_order_id_index", columnList = "order_id"),
                @Index(name = "item_option_id_index", columnList = "option_id"),
        })
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Option option;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Long price;

    @Builder
    public Item(Long id, Option option, Order order, Long quantity, Long price) {
        this.id = id;
        this.option = option;
        this.order = order;
        this.quantity = quantity;
        this.price = price;
    }
}
