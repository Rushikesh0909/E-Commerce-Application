package com.bikkadIt.repository;

import com.bikkadIt.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem,Integer> {

}
