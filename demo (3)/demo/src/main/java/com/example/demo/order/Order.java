package com.example.demo.order;

import com.example.demo.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "Order_tb",
        indexes = {
        @Index(name = "Order_user_id_index", columnList = "user_id")
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Order(Long id, User user) {
        this.id = id;
        this.user = user;
    }
}
