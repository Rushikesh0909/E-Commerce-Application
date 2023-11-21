package com.bikkadIt.controller;

import com.bikkadIt.constants.AppConstant;
import com.bikkadIt.dto.UserDto;
import com.bikkadIt.payloads.ApiResponse;
import com.bikkadIt.payloads.PageableResponse;
import com.bikkadIt.service.UserServiceI;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserServiceI userServiceI;

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){

        log.info("Entering the request for save user data:");
        UserDto user = this.userServiceI.createUser(userDto);
        log.info("Completed the request for save user data:");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @PutMapping("users/userId/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId){
        log.info("Entering the request for Update user data with userId :{}",userId);
        UserDto updateUser = this.userServiceI.updateUser(userDto, userId);
        log.info("Completed the request for Update user data with userId :{}",userId);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @GetMapping("/users/userId/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
        log.info("Entering the request for get user data with userId :{}",userId);
        UserDto userById = this.userServiceI.getUserById(userId);
        log.info("Completed the request for get user data with userId :{}",userId);
        return new ResponseEntity<>(userById,HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity <PageableResponse<UserDto>> getAllUser(@RequestParam (value = "pageSize",defaultValue = AppConstant.PAGE_SIZE)Integer pageSize,
                                                     @RequestParam (value= "pageNumber",defaultValue = AppConstant.PAGE_NUMBER)Integer pageNumber,
                                                     @RequestParam (value = "sortBy",defaultValue = AppConstant.SORT_BY,required = false)String sortBy,
                                                    @RequestParam (value = "sortDir",defaultValue = AppConstant.SORT_DIR,required = false)String sortDir){

        log.info("Entering the request for getAll user data  :");
        PageableResponse<UserDto> allUser = this.userServiceI.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed the request for getAll user data :");
        return new ResponseEntity<>(allUser,HttpStatus.OK);
    }

    @GetMapping("/users/keyword/{keyword}")
    public ResponseEntity<List<UserDto>>searchUser(@PathVariable String keyword){
        log.info("Entering the request for search user with keyword :{}",keyword);
        List<UserDto> userDtos = this.userServiceI.searchUser(keyword);
        log.info("Completed the request for search user with keyword :{}",keyword);
        return new ResponseEntity<>(userDtos,HttpStatus.OK);
    }

    @GetMapping("/users/email/{email}")
    public ResponseEntity<UserDto>getByEmail(@PathVariable String email){
        log.info("Entering the request for get user data with email :{}",email);
        UserDto byEmail = this.userServiceI.getByEmail(email);
        log.info("Completed the request for get user data with email :{}",email);
        return new ResponseEntity<>(byEmail,HttpStatus.OK);
    }

    @DeleteMapping("/users/userId/{userId}")
    public ResponseEntity<ApiResponse>deleteUser(@PathVariable String userId){
        log.info("Entering the request for delete user data with userId :{}",userId);
        ApiResponse apiResponse = ApiResponse.builder().message(AppConstant.DELETE).status(false).httpStatus(HttpStatus.OK).build();
        this.userServiceI.deleteUser(userId);
        log.info("Completed the request for delete user data with userId :{}",userId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }


}
