package com.bikkadIt.service.impl;

import com.bikkadIt.constants.AppConstant;
import com.bikkadIt.dto.ProductDto;
import com.bikkadIt.entity.Category;
import com.bikkadIt.entity.Product;
import com.bikkadIt.exception.ResourseNotFoundException;
import com.bikkadIt.payloads.PageableResponse;
import com.bikkadIt.payloads.helper;
import com.bikkadIt.repository.CategoryRepo;
import com.bikkadIt.repository.ProductRepo;
import com.bikkadIt.service.ProductServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductServiceI {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public ProductDto createProduct(ProductDto product) {
        Product product1 = this.modelMapper.map(product, Product.class);
        String uuid = UUID.randomUUID().toString();
        product1.setProductId(uuid);
        Date date=new Date();
        product1.setAddedDate(date);
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
//        product.setAddedDate(productDto.getAddedDate());
        product.setLive(productDto.getLive());
        product.setStock(productDto.getStock());
        Date date=new Date();
        product.setAddedDate(date);
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
    public PageableResponse<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }else {
            sort=Sort.by(sortBy).descending();
        }
        PageRequest request = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> products = this.productRepo.findAll(request);
        PageableResponse<ProductDto> pageableResponse = helper.getPageableResponse(products, ProductDto.class);

        return pageableResponse;
    }


    @Override
    public void deleteProduct(String productId) {
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND + productId));

        this.productRepo.delete(product);

    }

    @Override
    public PageableResponse<ProductDto> findByLiveTrue(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }else {
            sort=Sort.by(sortBy).descending();
        }
        PageRequest request = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> byLiveTrue = this.productRepo.findByLiveTrue(request);
        PageableResponse<ProductDto> pageableResponse = helper.getPageableResponse(byLiveTrue, ProductDto.class);
        return pageableResponse;
    }



    @Override
    public PageableResponse<ProductDto> findByTitleContaining(Integer pageNumber, Integer pageSize, String sortBy,String sortDir, String keyword) {
        Sort sort;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }else {
            sort=Sort.by(sortBy).descending();
        }
        PageRequest pagerequest = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> titleContaining = this.productRepo.findByTitleContaining(pagerequest, keyword);
        PageableResponse<ProductDto> pageableResponse = helper.getPageableResponse(titleContaining, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProducts(Integer pageNumber, Integer pageSize, String sortBy, String direction) {
        Sort sort;
        if(direction.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }else {
            sort=Sort.by(sortBy).descending();
        }
        PageRequest pagerequest = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> products = this.productRepo.findAll(pagerequest);
        PageableResponse<ProductDto> pageableResponse = helper.getPageableResponse(products, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND));
        Product product = this.modelMapper.map(productDto, Product.class);

        Date date=new Date();
        String id = UUID.randomUUID().toString();
        product.setProductId(id);
        product.setAddedDate(date);
        product.setCategories(category);
        Product newProduct=this.productRepo.save(product);
        return modelMapper.map(newProduct,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId, Integer pageNumber, Integer pageSize, String sortBy, String direction) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND));
        Sort sort=(direction.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        PageRequest pages = PageRequest.of(pageNumber, pageSize,sort);

        Page<Product> products = this.productRepo.findByCategories(category, pages);
        PageableResponse<ProductDto> pageableResponse = helper.getPageableResponse(products, ProductDto.class);

        return pageableResponse;
    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND));
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND));

        product.setCategories(category);
        Product save = this.productRepo.save(product);
        ProductDto dto = modelMapper.map(save, ProductDto.class);
        return dto;
    }


}
