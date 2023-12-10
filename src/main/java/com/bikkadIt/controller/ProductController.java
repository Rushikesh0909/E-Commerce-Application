package com.bikkadIt.controller;

import com.bikkadIt.constants.AppConstant;
import com.bikkadIt.dto.ProductDto;
import com.bikkadIt.payloads.ApiResponse;
import com.bikkadIt.payloads.PageableResponse;
import com.bikkadIt.service.ProductServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductServiceI productServiceI;

    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {

        ProductDto product = this.productServiceI.createProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/productId/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable String productId) {
        ProductDto updateProduct = this.productServiceI.updateProduct(productDto, productId);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    @GetMapping("/productId/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId) {
        ProductDto product = this.productServiceI.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<PageableResponse> getAllProducts(@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                           @RequestParam(value = "sortBy", defaultValue = AppConstant.PRODUCT_SORT_BY, required = false) String sortBy,
                                                           @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction) {
        PageableResponse<ProductDto> allProduct = this.productServiceI.getAllProduct(pageNumber, pageSize, sortBy, direction);
        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }

    @DeleteMapping("/productId/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId) {
        ApiResponse apiResponse = ApiResponse.builder().message(AppConstant.DELETE).status(false).httpStatus(HttpStatus.OK).build();
        this.productServiceI.deleteProduct(productId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/liveTrue")
    public ResponseEntity<PageableResponse>getByLiveTrue(@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                         @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                         @RequestParam(value = "sortBy", defaultValue = AppConstant.PRODUCT_SORT_BY, required = false) String sortBy,
                                                         @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction) {
        PageableResponse<ProductDto> byLiveTrue = this.productServiceI.findByLiveTrue(pageNumber, pageSize, sortBy, direction);
       return new ResponseEntity<>(byLiveTrue,HttpStatus.OK);

    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<PageableResponse>getByTitleContaining(@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                                @RequestParam(value = "sortBy", defaultValue = AppConstant.PRODUCT_SORT_BY, required = false) String sortBy,
                                                                @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction,
                                                                @PathVariable String keyword) {
        PageableResponse<ProductDto> titleContaining = this.productServiceI.findByTitleContaining(pageNumber, pageSize, sortBy, direction, keyword);
        return new ResponseEntity<>(titleContaining,HttpStatus.OK);
    }

    @GetMapping("/allLiveProduct")
    public ResponseEntity<PageableResponse>getAllLiveProducts(@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                              @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                              @RequestParam(value = "sortBy", defaultValue = AppConstant.PRODUCT_SORT_BY, required = false) String sortBy,
                                                              @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction) {
        PageableResponse<ProductDto> allLiveProducts = this.productServiceI.getAllLiveProducts(pageNumber, pageSize, sortBy, direction);
        return new ResponseEntity<>(allLiveProducts,HttpStatus.OK);
    }
    }
