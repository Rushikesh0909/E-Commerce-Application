package com.bikkadIt.controller;

import com.bikkadIt.constants.AppConstant;
import com.bikkadIt.dto.CategoryDto;
import com.bikkadIt.dto.UserDto;
import com.bikkadIt.payloads.ApiResponse;
import com.bikkadIt.payloads.ImageResponse;
import com.bikkadIt.payloads.PageableResponse;
import com.bikkadIt.service.CategoryServiceI;
import com.bikkadIt.service.FileService;
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

@RestController
@RequestMapping("/api")
@Slf4j
public class CategoryController {

    @Autowired
    private FileService fileService;
    @Autowired
    private CategoryServiceI categoryServiceI;

    @Value("${Category.profile.image.path}")
    private String path;

    /**
     * @param categoryDto
     * @return CategoryDto
     * @author Rushikesh Hatkar
     * @apiNote save Category Into Database
     * @since 1.0v
     */
    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> saveCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Enter the  request for Save the Category ");
        CategoryDto category = this.categoryServiceI.createCategory(categoryDto);
        log.info("Completed the  request for Save the Category ");
        return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return PageableResponse
     * @author Rushikesh Hatkar
     * @apiNote get All Category
     * @since 1.0v
     */
    @GetMapping("/categories")
    public ResponseEntity<PageableResponse> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.CATEGORY_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction

    ) {
        log.info("Enter the  request for Get All the Category :");
        PageableResponse<CategoryDto> allCategory = this.categoryServiceI.getAllCategory(pageNumber, pageSize, sortBy, direction);
        log.info("Completed the  request for Get All the Category :");
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }

    /**
     * @param categoryId
     * @return CategoryDto
     * @author Rushikesh Hatkar
     * @apiNote get Single User By UserId
     * @since 1.0v
     */
    @GetMapping("/categories/categoryId/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable String categoryId) {
        log.info("Enter the  request for Get  the Category With Category Id  :{}", categoryId);
        CategoryDto singleCategory = this.categoryServiceI.getSingleCategory(categoryId);
        log.info("Completed the  request for Get  the Category With Category Id  :{}", categoryId);
        return new ResponseEntity<CategoryDto>(singleCategory, HttpStatus.OK);
    }

    /**
     * @param categoryId
     * @return Api Response
     * @author Rushikesh Hatkar
     * @apiNote delete Category By Id
     * @since 1.0v
     */
    @DeleteMapping("/categories/categoryId/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId) {
        log.info("Enter the  request for Delete  the Category With Category Id  :{}", categoryId);
        ApiResponse api = new ApiResponse();
        api.setMessage(AppConstant.DELETE);
        api.setHttpStatus(HttpStatus.OK);
        api.setStatus(true);
        this.categoryServiceI.deleteCategory(categoryId);
        log.info("Completed the  request for Delete  the Category With Category Id  :{}", categoryId);
        return new ResponseEntity<>(api, HttpStatus.OK);
    }

    /**
     * @param dto
     * @param categoryId
     * @return CategoryDto
     * @athor Rushikesh Hatkar
     * @apiNote uodate Category By Id
     * @since 1.0v
     */
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto dto, @PathVariable String categoryId) {
        log.info("Enter the  request for Update  the Category With Category Id  :{}", categoryId);
        CategoryDto categoryDto1 = this.categoryServiceI.updateCategory(dto, categoryId);
        log.info("Completed the  request for Update  the Category With Category Id  :{}", categoryId);
        return new ResponseEntity<>(categoryDto1, HttpStatus.OK);
    }


    @PostMapping("/categories/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam MultipartFile image, @PathVariable String categoryId) throws IOException {
        log.info("Entering the request for upload image with categoryId:{}", categoryId);
        String imageName = this.fileService.uploadFile(image, path);
        CategoryDto category = this.categoryServiceI.getSingleCategory(categoryId);
        category.setCoverImage(imageName);
        CategoryDto updateCategory = this.categoryServiceI.updateCategory(category, categoryId);
        ImageResponse imageResponse = ImageResponse.builder().message("Image Upload Successfully").imageName(imageName).status(true).httpStatus(HttpStatus.CREATED).build();
        log.info("Completed the request for upload image with categoryId:{}", categoryId);
        return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/categories/image/{categoryId}")
    public void getCategoryImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        log.info("Enter the request for Get Image with categoryId : {}", categoryId);
        CategoryDto singleCategory = categoryServiceI.getSingleCategory(categoryId);
        log.info("CategoryImage Name : {}", singleCategory.getCoverImage());
        InputStream resource = fileService.getResource(path, singleCategory.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        log.info("Completed the request for Get Image with categoryId : {}", categoryId);
        StreamUtils.copy(resource, response.getOutputStream());

    }
}
