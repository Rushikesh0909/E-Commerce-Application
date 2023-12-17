package com.bikkadIt.dto;

import com.bikkadIt.entity.Product;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {


    private Integer cartItemId;

    private Integer quantity;

    private Integer totalPrice;

    private ProductDto product;
}
