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
        @Index(name = "Order_user_id_index", columnList = "user_id")     // ** DB의 이름 설정
})
public class Order {
    // ** PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // ** User를 불러옴
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Order(Long id, User user) {
        this.id = id;
        this.user = user;
    }
}
