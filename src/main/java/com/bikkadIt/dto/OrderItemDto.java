package com.bikkadIt.dto;

import com.bikkadIt.entity.Order;
import com.bikkadIt.entity.Product;
import lombok.*;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderItemDto {

    private Integer orderItemId;

    private Integer quantity;

    private Integer totalPrice;

    private ProductDto product;

}
