package com.bikkadIt.service;

import com.bikkadIt.dto.ProductDto;

public interface ProductServiceI {

    // create
    ProductDto createProduct(ProductDto product);

    // update
    ProductDto updateProduct(ProductDto productDto,String productId);

    // get single
    ProductDto getProductById(String productId);

    // get all

    // delete
    void deleteProduct(String productId);

    // search


}
