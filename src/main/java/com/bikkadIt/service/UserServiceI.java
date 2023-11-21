package com.bikkadIt.service;

import com.bikkadIt.dto.UserDto;
import com.bikkadIt.entity.User;
import com.bikkadIt.payloads.PageableResponse;

import java.util.List;

public interface UserServiceI {

    //create
    UserDto createUser(UserDto user);

    //update
    UserDto updateUser(UserDto userDto,String userId);

    // get single user
    UserDto getUserById(String userId);

    // get all user
    PageableResponse<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    // delete user
    void deleteUser(String userId);


    // search user
    List<UserDto> searchUser(String keyword);

     // get by email
        UserDto getByEmail(String email);
}
