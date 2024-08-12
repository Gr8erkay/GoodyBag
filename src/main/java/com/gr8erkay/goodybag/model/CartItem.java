package com.gr8erkay.goodybag.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id", referencedColumnName = "goods_id")
    private Goods goods;

    @Column(nullable = false)
    private int quantity;
}
