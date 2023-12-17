package com.bikkadIt.controller;

import com.bikkadIt.dto.CategoryDto;
import com.bikkadIt.dto.ProductDto;
import com.bikkadIt.entity.Category;
import com.bikkadIt.entity.Product;
import com.bikkadIt.payloads.PageableResponse;
import com.bikkadIt.service.CategoryServiceI;
import com.bikkadIt.service.ProductServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceI productServiceI;

    @Autowired
    private ModelMapper modelMapper;

    Product product;
    Product product1;

    @BeforeEach
    public void init() {
        product = Product.builder().title("Apple").description("This Launched in 2023").price(50000.00)
                .discountedPrice(45000.00).quantity(15)
                .live(true).stock(true).build();

        product1 = Product.builder().title("OnePlus").description("This Launched in 2022").price(100000.00)
                .discountedPrice(95000.00).quantity(5)
                .live(true).stock(true).build();
    }

    private String convertObjectToJsonString(Product product)  {
        try {
            return new ObjectMapper().writeValueAsString(product);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void createProductTest() throws Exception {
        ProductDto dto = modelMapper.map(product, ProductDto.class);
        Mockito.when(this.productServiceI.createProduct(Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/products/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());

    }

    @Test
    public void updateProductTest() throws Exception {

        String productId="abc";
        ProductDto dto = this.modelMapper.map(product, ProductDto.class);
        Mockito.when(this.productServiceI.updateProduct(Mockito.any(),Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/products/productId/"+productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }

    @Test
    public void getSingleProductTest() throws Exception {

        String productId="abc";
        ProductDto dto = this.modelMapper.map(product, ProductDto.class);
        Mockito.when(this.productServiceI.getProductById(productId)).thenReturn(dto);
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/productId/"+productId)
                     ).andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void getAllProductTest() throws Exception {
        ProductDto productDto = ProductDto.builder().title("Apple").description("This Launched in 2023").price(50000.00)
                .discountedPrice(45000.00).quantity(15)
                .live(true).stock(true).build();

        ProductDto productDto1 = ProductDto.builder().title("Apple").description("This Launched in 2022").price(100000.00)
                .discountedPrice(95000.00).quantity(5)
                .live(true).stock(true).build();

        PageableResponse<ProductDto> pageResponse=new PageableResponse<>();
        pageResponse.setContent(Arrays.asList(productDto,productDto1));
        pageResponse.setLastPage(false);
        pageResponse.setPageNumber(100);
        pageResponse.setPageSize(10);
        pageResponse.setTotalElement(1000);

        Mockito.when(this.productServiceI.getAllProduct(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageResponse);
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/")

        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void deleteProductTest() throws Exception {

        String productId="abc";
        Mockito.doNothing().when(this.productServiceI).deleteProduct(productId);
        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/products/productId/"+productId)
        ).andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void findByLivetrueTest() throws Exception {
        ProductDto productDto = ProductDto.builder().title("Apple").description("This Launched in 2023").price(50000.00)
                .discountedPrice(45000.00).quantity(15)
                .live(true).stock(true).build();

        ProductDto productDto1 = ProductDto.builder().title("Apple").description("This Launched in 2022").price(100000.00)
                .discountedPrice(95000.00).quantity(5)
                .live(true).stock(true).build();

        PageableResponse<ProductDto> pageResponse=new PageableResponse<>();
        pageResponse.setContent(Arrays.asList(productDto,productDto1));
        pageResponse.setLastPage(false);
        pageResponse.setPageNumber(100);
        pageResponse.setPageSize(10);
        pageResponse.setTotalElement(1000);

        Mockito.when(this.productServiceI.findByLiveTrue(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageResponse);
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/liveTrue")

        ).andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void getProductByTitleTest() throws Exception {
        ProductDto productDto = ProductDto.builder().title("Apple").description("This Launched in 2023").price(50000.00)
                .discountedPrice(45000.00).quantity(15)
                .live(true).stock(true).build();

        ProductDto productDto1 = ProductDto.builder().title("Apple").description("This Launched in 2022").price(100000.00)
                .discountedPrice(95000.00).quantity(5)
                .live(true).stock(true).build();

        PageableResponse<ProductDto> pageResponse=new PageableResponse<>();
        pageResponse.setContent(Arrays.asList(productDto,productDto1));
        pageResponse.setLastPage(false);
        pageResponse.setPageNumber(100);
        pageResponse.setPageSize(10);
        pageResponse.setTotalElement(1000);

        String keyword="Apple";
        Mockito.when(this.productServiceI.findByTitleContaining(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/keyword/"+keyword)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());

    }

}
