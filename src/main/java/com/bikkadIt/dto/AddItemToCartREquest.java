package com.bikkadIt.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddItemToCartREquest {

    private String productId;

    private Integer quantity;
}
