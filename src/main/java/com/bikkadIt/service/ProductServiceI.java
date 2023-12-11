package com.bikkadIt.service;

import com.bikkadIt.dto.ProductDto;
import com.bikkadIt.entity.Product;
import com.bikkadIt.payloads.PageableResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductServiceI {

    // create
    ProductDto createProduct(ProductDto product);

    // update
    ProductDto updateProduct(ProductDto productDto,String productId);

    // get single
    ProductDto getProductById(String productId);

    // get all
    PageableResponse<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    // delete
    void deleteProduct(String productId);


    PageableResponse<ProductDto> findByLiveTrue(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> findByTitleContaining(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, String keyword);

    PageableResponse<ProductDto> getAllLiveProducts(Integer pageNumber, Integer pageSize, String sortBy, String direction);


    ProductDto createProductWithCategory(ProductDto productDto,String categoryId);

    PageableResponse<ProductDto> getAllOfCategory(String categoryId,Integer pageNumber,Integer pageSize, String sortBy, String direction );

    ProductDto updateCategory(String productId,String categoryId);

}
