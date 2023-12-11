package com.bikkadIt.repository;

import com.bikkadIt.entity.Category;
import com.bikkadIt.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,String> {

    Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product> findByTitleContaining(Pageable pageable, String keyword);


    Page<Product> findByCategories(Category category,Pageable pageable);
}
