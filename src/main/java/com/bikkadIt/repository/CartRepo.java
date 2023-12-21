package com.bikkadIt.repository;

import com.bikkadIt.entity.Cart;
import com.bikkadIt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,String> {


   Optional<Cart>findByUser(User user);
}
