package com.bikkadIt.service.impl;

import com.bikkadIt.constants.AppConstant;
import com.bikkadIt.dto.ProductDto;
import com.bikkadIt.entity.Product;
import com.bikkadIt.exception.ResourseNotFoundException;
import com.bikkadIt.repository.ProductRepo;
import com.bikkadIt.service.ProductServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductServiceI {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto createProduct(ProductDto product) {
        Product product1 = this.modelMapper.map(product, Product.class);
        String uuid = UUID.randomUUID().toString();
        product1.setProductId(uuid);
        Product saveProduct = this.productRepo.save(product1);
        ProductDto productDto = this.modelMapper.map(saveProduct, ProductDto.class);
        return productDto;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND + productId));

        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setAddedDate(productDto.getAddedDate());
        product.setLive(productDto.getLive());
        product.setStock(productDto.getStock());

        Product updatedProduct = this.productRepo.save(product);
        return this.modelMapper.map(updatedProduct,ProductDto.class);
    }

    @Override
    public ProductDto getProductById(String productId) {

        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND+productId));
        ProductDto productDto = this.modelMapper.map(product, ProductDto.class);
        return productDto;
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND + productId));

        this.productRepo.delete(product);

    }
}
