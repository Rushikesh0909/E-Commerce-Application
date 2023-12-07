package com.bikkadIt.repository;

import com.bikkadIt.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,String> {


}
