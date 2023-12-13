package com.bikkadIt.controller;

import com.bikkadIt.constants.AppConstant;
import com.bikkadIt.dto.ProductDto;
import com.bikkadIt.payloads.ApiResponse;
import com.bikkadIt.payloads.ImageResponse;
import com.bikkadIt.payloads.PageableResponse;
import com.bikkadIt.service.FileService;
import com.bikkadIt.service.ProductServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductServiceI productServiceI;

    @Autowired
    private FileService fileService;

    @Value("${Product.profile.image.path}")
    private String path;

    /**
     * @author RushiKesh Hatkar
     * @apiNote For create product
     * @param productDto
     * @return
     * @since 1.0 v
     */
    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Enter the  request for Save the Product");
        ProductDto product = this.productServiceI.createProduct(productDto);
        log.info("Completed the  request for Save the Product");
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    /**
     * @author Rushikesh Hatkar
     * @apiNote For update product
     * @param productDto
     * @param productId
     * @return
     * @since 1.0 v
     */
    @PutMapping("/productId/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable String productId) {
        log.info("Enter the  request for update the Product with productId:{}",productId);
        ProductDto updateProduct = this.productServiceI.updateProduct(productDto, productId);
        log.info("Completed the  request for update the Product with productId:{}",productId);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    /**
     * @author Rushikesh Hatkar
     * @apiNote For get single product
     * @param productId
     * @return
     * @since 1.0 v
     */
    @GetMapping("/productId/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId) {
        log.info("Enter the  request for get single Product with productId:{}",productId);
        ProductDto product = this.productServiceI.getProductById(productId);
        log.info("Completed the  request for get single Product with productId:{}",productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * @author Rushikesh Hatkar
     * @apiNote For get all products
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return
     * @since 1.0 v
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse> getAllProducts(@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                           @RequestParam(value = "sortBy", defaultValue = AppConstant.PRODUCT_SORT_BY, required = false) String sortBy,
                                                           @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction) {
        log.info("Enter the  request for get all Products");
        PageableResponse<ProductDto> allProduct = this.productServiceI.getAllProduct(pageNumber, pageSize, sortBy, direction);
        log.info("Completed the  request for get all Products");
        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }

    /**
     * @author Rushikesh Hatkar
     * @apiNote For delete product
     * @param productId
     * @return
     * @since 1.0 v
     */
    @DeleteMapping("/productId/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId) {
        log.info("Enter the  request for delete single Product with productId:{}",productId);
        ApiResponse apiResponse = ApiResponse.builder().message(AppConstant.DELETE).status(false).httpStatus(HttpStatus.OK).build();
        this.productServiceI.deleteProduct(productId);
        log.info("Completed the  request for delete single Product with productId:{}",productId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * @author Rushikesh Hatkar
     * @apiNote For get live true
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return
     * @since 1.0 v
     */
    @GetMapping("/liveTrue")
    public ResponseEntity<PageableResponse> getByLiveTrue(@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                          @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                          @RequestParam(value = "sortBy", defaultValue = AppConstant.PRODUCT_SORT_BY, required = false) String sortBy,
                                                          @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction) {
        log.info("Enter the  request for get by Live true");
        PageableResponse<ProductDto> byLiveTrue = this.productServiceI.findByLiveTrue(pageNumber, pageSize, sortBy, direction);
        log.info("Completed the  request for get by Live true");
        return new ResponseEntity<>(byLiveTrue, HttpStatus.OK);

    }

    /**
     * @author Rushikesh Hatkar
     * @apiNote For get By Title
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @param keyword
     * @return
     * @since 1.0 v
     */
    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<PageableResponse> getByTitleContaining(@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                 @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                                 @RequestParam(value = "sortBy", defaultValue = AppConstant.PRODUCT_SORT_BY, required = false) String sortBy,
                                                                 @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction,
                                                                 @PathVariable String keyword) {
        log.info("Enter the  request for get by title with keyword:{}",keyword);
        PageableResponse<ProductDto> titleContaining = this.productServiceI.findByTitleContaining(pageNumber, pageSize, sortBy, direction, keyword);
        log.info("Completed the  request for get by title with keyword:{}",keyword);
        return new ResponseEntity<>(titleContaining, HttpStatus.OK);
    }

    /**
     * @author Rushikesh Hatkar
     * @apiNote For get All live products
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return
     * @since 1.0 v
     */
    @GetMapping("/allLiveProduct")
    public ResponseEntity<PageableResponse> getAllLiveProducts(@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                               @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                               @RequestParam(value = "sortBy", defaultValue = AppConstant.PRODUCT_SORT_BY, required = false) String sortBy,
                                                               @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction) {
        log.info("Enter the  request for get All live products");
        PageableResponse<ProductDto> allLiveProducts = this.productServiceI.getAllLiveProducts(pageNumber, pageSize, sortBy, direction);
        log.info("Completed the  request for get All live products");
        return new ResponseEntity<>(allLiveProducts, HttpStatus.OK);
    }

    /**
     * @author Rushikesh Hatkar
     * @apiNote For save Product with categoryId
     * @param categoryId
     * @param productDto
     * @return
     * @since 1.0 v
     */
    @PostMapping("/category/{categoryId}")
    public ResponseEntity<ProductDto> saveProductWithCategoryId(@PathVariable String categoryId, @RequestBody ProductDto productDto) {

        log.info("Enter the  request for save product with categoryId:{}",categoryId);
        ProductDto product = this.productServiceI.createProductWithCategory(productDto, categoryId);
        log.info("Completed the  request for save product with categoryId:{}",categoryId);
        return new ResponseEntity<>(product, HttpStatus.CREATED);

    }

    /**
     * @author Rushikesh Hatkar
     * @apiNote get all products by categoryId
     * @param categoryId
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return
     * @since 1.0 v
     */
    @GetMapping("/categoryId/{categoryId}")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProductsByCategoryId(
            @PathVariable String categoryId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false)String direction){
        log.info("Enter the  request for get all product with categoryId:{}",categoryId);
        PageableResponse<ProductDto> allproducts = this.productServiceI.getAllProductByCategoryId(categoryId, pageNumber, pageSize, sortBy, direction);
        log.info("Completed the  request for get all product with categoryId:{}",categoryId);
        return new ResponseEntity<>(allproducts,HttpStatus.OK);
    }



    /**
     * @author Rushikesh Hatkar
     * @apiNote Update product with category
     * @param productId
     * @param categoryId
     * @return
     * @since 1.0 v
     */

    @PutMapping("/productId/{productId}/categoryId/{categoryId}")
    public ResponseEntity<ProductDto> updateProductWithCategory(@PathVariable String productId, @PathVariable String categoryId) {
        log.info("Enter the  request for update product with productId and categoryId:{} :{}",productId,categoryId);
        ProductDto productDto = this.productServiceI.updateProductByCategory(productId, categoryId);
        log.info("Completed the  request for update product with productId and categoryId:{} :{}",productId,categoryId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    /**
     * @author Rushikesh Hatkar
     * @apiNote For upload image
     * @param image
     * @param productId
     * @return
     * @throws IOException
     * @since 1.0 v
     */
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam MultipartFile image, @PathVariable String productId) throws IOException {

        log.info("Enter the  request for upload product image with productId:{}",productId);
        String file = this.fileService.uploadFile(image, path);

        ProductDto product = this.productServiceI.getProductById(productId);

        product.setImage(file);

        ProductDto updatedProduct = this.productServiceI.updateProduct(product, productId);

        ImageResponse imageResponse = ImageResponse.builder().message("Image Uploaded").imageName(file).status(true).httpStatus(HttpStatus.CREATED).build();
        log.info("Completed the  request for upload product image with productId:{}",productId);
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);

    }

    /**
     * @author Rushikesh Hatkar
     * @apiNote get Product image
     * @param productId
     * @param response
     * @throws IOException
     * @since 1.0 v
     */
    @GetMapping("/image/{ProductId}")
    public void getProductImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        log.info("Enter the  request for upload product image with productId:{}",productId);
        ProductDto product = this.productServiceI.getProductById(productId);
        InputStream resource = this.fileService.getResource(path, product.getImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        log.info("Completed the  request for upload product image with productId:{}",productId);

    }
}
