package com.bikkadIt.services;

import com.bikkadIt.dto.ProductDto;
import com.bikkadIt.entity.Category;
import com.bikkadIt.entity.Product;
import com.bikkadIt.payloads.PageableResponse;
import com.bikkadIt.repository.CategoryRepo;
import com.bikkadIt.repository.ProductRepo;
import com.bikkadIt.service.CategoryServiceI;
import com.bikkadIt.service.ProductServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepo productRepo;

    @Autowired
    private ProductServiceI productServiceI;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepo categoryRepo;

    Product product;

    Product product1;

    @BeforeEach
    public void init() {
        product = Product.builder().title("Apple").description("This Launched in 2023").price(50000.00)
                .discountedPrice(45000.00).quantity(15)
                .live(true).stock(true).build();

        product1 = Product.builder().title("Apple").description("This Launched in 2022").price(100000.00)
                .discountedPrice(95000.00).quantity(5)
                .live(true).stock(true).build();
    }

    @Test
    public void createProductTest() {

        Mockito.when(this.productRepo.save(Mockito.any())).thenReturn(product);
        ProductDto product1 = this.productServiceI.createProduct(modelMapper.map(product, ProductDto.class));
        System.out.println(product1.getTitle());
        Assertions.assertNotNull(product1);

    }

    @Test
    public void updateProductTest() {

        ProductDto productDto = ProductDto.builder().title("One plus").description("This Launched in 2023").price(50000.00)
                .discountedPrice(45000.00).quantity(15)
                .live(true).stock(true).build();

        Mockito.when(this.productRepo.findById("productId")).thenReturn(Optional.of(product));
        Mockito.when(this.productRepo.save(product)).thenReturn(product);
        ProductDto productDto1 = this.productServiceI.updateProduct(productDto, "productId");
        Assertions.assertNotNull(productDto1);
        Assertions.assertEquals("One plus", productDto1.getTitle());
    }

    @Test
    public void getProductByIdTest() {

        String productId = "acb";
        Mockito.when(this.productRepo.findById(productId)).thenReturn(Optional.of(product));
        ProductDto productById = this.productServiceI.getProductById(productId);
        Assertions.assertNotNull(productById);
        Assertions.assertEquals(product.getTitle(), productById.getTitle());
    }

    @Test
    public void getAllProduct() {

        List<Product> products = Arrays.asList(product, product1);
        Page<Product> page = new PageImpl<>(products);
        Mockito.when(this.productRepo.findAll((Pageable) Mockito.any())).thenReturn(page);
        Sort.by("title").ascending();
    }

    @Test
    public void deleteProductTest() {
        String productId = "abc";

        Mockito.when(this.productRepo.findById(productId)).thenReturn(Optional.of(product));
        this.productServiceI.deleteProduct(productId);
        Mockito.verify(productRepo, Mockito.times(1)).delete(product);
    }

    @Test
    public void findByLivetrueTest() {
        List<Product> products = Arrays.asList(product, product1);
        Page<Product> page = new PageImpl<>(products);
        Mockito.when(this.productRepo.findByLiveTrue((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> byLiveTrue = this.productServiceI.findByLiveTrue(1, 10, "title", "asc");
        Assertions.assertNotNull(byLiveTrue);
        List<ProductDto> collect = byLiveTrue.getContent().stream().filter(e -> e.getLive() == true).collect(Collectors.toList());
        Assertions.assertEquals("One plus", product.getTitle());

    }

    @Test
    public void getProductByTitle() {
        String keyword = "App";
        List<Product> products = Arrays.asList(product, product1);
        Page<Product> page = new PageImpl<>(products);

        Mockito.when(this.productRepo.findByTitleContaining((Pageable) Mockito.any(), Mockito.anyString())).thenReturn(page);
        PageableResponse<ProductDto> actualResult = this.productServiceI.findByTitleContaining(1, 10, "title", "desc", keyword);
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(2,actualResult.getContent().size());

    }
//    @Test
//    public void createProductWithCategoryIdTest(){
//
//        String categoryId="abc";
//
//        Category categoryDto=Category.builder()
//                .title("Oppo")
//                .description("This Is Oppo")
//                .coverImage("abc.png").build();
//
//        Mockito.when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(categoryDto));
//
//        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product);
//
//        ProductDto productDto = this.productServiceI.createProductWithCategory(this.modelMapper.map(product, ProductDto.class), Mockito.anyString());
//        Assertions.assertNotNull(productDto);
//
//        Assertions.assertEquals("abc",productDto.getProductId());
//        Assertions.assertEquals(categoryId,productDto.getCategories());
//    }
}
