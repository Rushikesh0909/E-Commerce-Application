package com.bikkadIt.controller;

import com.bikkadIt.constants.AppConstant;
import com.bikkadIt.dto.UserDto;
import com.bikkadIt.payloads.ApiResponse;
import com.bikkadIt.payloads.ImageResponse;
import com.bikkadIt.payloads.PageableResponse;
import com.bikkadIt.service.FileService;
import com.bikkadIt.service.UserServiceI;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
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
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String path;

    /**
     * @param userDto
     * @return
     * @author Rushikesh Hatkar
     * @apiNote For create users
     * @since 1.0 v
     */
    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

        log.info("Entering the request for save user data:");
        UserDto user = this.userServiceI.createUser(userDto);
        log.info("Completed the request for save user data:");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * @param userDto
     * @param userId
     * @return
     * @throws
     * @author Rushikesh Hatkar
     * @apiNote For update single user
     * @since 1.0 v
     */
    @PutMapping("users/userId/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {
        log.info("Entering the request for Update user data with userId :{}", userId);
        UserDto updateUser = this.userServiceI.updateUser(userDto, userId);
        log.info("Completed the request for Update user data with userId :{}", userId);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return
     * @author Rushikesh Hatkar
     * @apiNote get single user
     * @since 1.0 v
     */
    @GetMapping("/users/userId/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        log.info("Entering the request for get user data with userId :{}", userId);
        UserDto userById = this.userServiceI.getUserById(userId);
        log.info("Completed the request for get user data with userId :{}", userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    /**
     * @param pageSize
     * @param pageNumber
     * @param sortBy
     * @param sortDir
     * @return
     * @author Rushikesh Hatkar
     * @apiNote get all users
     * @since 1.0 v
     */
    @GetMapping("/users")
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(@RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE) Integer pageSize,
                                                                @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER) Integer pageNumber,
                                                                @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
                                                                @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {

        log.info("Entering the request for getAll user data  :");
        PageableResponse<UserDto> allUser = this.userServiceI.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed the request for getAll user data :");
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    /**
     * @param keyword
     * @return
     * @author Rushikesh Hatkar
     * @apiNote get users by using keyword
     * @since 1.0 v
     */
    @GetMapping("/users/keyword/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        log.info("Entering the request for search user with keyword :{}", keyword);
        List<UserDto> userDtos = this.userServiceI.searchUser(keyword);
        log.info("Completed the request for search user with keyword :{}", keyword);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    /**
     * @param email
     * @return
     * @author Rushikesh Hatkar
     * @apiNote get single user by email
     * @since 1.0 v
     */
    @GetMapping("/users/email/{email}")
    public ResponseEntity<UserDto> getByEmail(@PathVariable String email) {
        log.info("Entering the request for get user data with email :{}", email);
        UserDto byEmail = this.userServiceI.getByEmail(email);
        log.info("Completed the request for get user data with email :{}", email);
        return new ResponseEntity<>(byEmail, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return
     * @author Rushikesh Hatkar
     * @apiNote delete single user
     * @since 1.0 v
     */
    @DeleteMapping("/users/userId/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        log.info("Entering the request for delete user data with userId :{}", userId);
        ApiResponse apiResponse = ApiResponse.builder().message(AppConstant.DELETE).status(false).httpStatus(HttpStatus.OK).build();
        this.userServiceI.deleteUser(userId);
        log.info("Completed the request for delete user data with userId :{}", userId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/users/image/{userId}")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam MultipartFile image, @PathVariable String userId) throws IOException {
        log.info("Entering the request for upload image with userId:{}", userId);
        String imageName = this.fileService.uploadFile(image, path);
        UserDto user = this.userServiceI.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = this.userServiceI.updateUser(user, userId);
        ImageResponse imageResponse = ImageResponse.builder().message("Image Upload Successfully").imageName(imageName).status(true).httpStatus(HttpStatus.CREATED).build();
        log.info("Completed the request for upload image with userId:{}", userId);
        return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/image/{userId}")
    public void getUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        log.info("Enter the request for Get Image with UserId : {}", userId);
        UserDto user = userServiceI.getUserById(userId);
        log.info(" UserImage Name : {}", user.getImageName());
        InputStream resource = fileService.getResource(path, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        log.info("Completed the request for Get Image with UserId : {}", userId);
        StreamUtils.copy(resource, response.getOutputStream());

    }
}