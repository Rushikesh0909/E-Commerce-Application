package com.bikkadIt.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemId;

    @Column(name = "cart_quatity")
    private Integer quantity;

    private Integer totalPrice;

    @OneToOne
    @JoinColumn(name="product_Id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_Id")
    private Cart cart;
}
