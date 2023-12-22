package com.bikkadIt.repository;

import com.bikkadIt.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem,Integer> {


}
