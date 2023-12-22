package com.bikkadIt.repository;

import com.bikkadIt.entity.Order;
import com.bikkadIt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,String> {

List<Order>findByUser(User user);
}
