package com.bikkadIt.dto;

import com.bikkadIt.entity.CartItem;
import com.bikkadIt.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {


    private String cartId;

    private Date createdAt;

    private UserDto user;

    private List<CartItemDto> items = new ArrayList<>();


}
