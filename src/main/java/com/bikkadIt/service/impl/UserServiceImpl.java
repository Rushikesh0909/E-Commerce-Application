package com.bikkadIt.service.impl;

import com.bikkadIt.constants.AppConstant;
import com.bikkadIt.dto.UserDto;
import com.bikkadIt.entity.User;
import com.bikkadIt.exception.ResourseNotFoundException;
import com.bikkadIt.payloads.PageableResponse;
import com.bikkadIt.payloads.helper;
import com.bikkadIt.repository.UserRepo;
import com.bikkadIt.service.UserServiceI;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserServiceI {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto user) {
        log.info("Entering dao call for save user data :{}");
        User user1 = this.modelMapper.map(user, User.class);
        String uuid = UUID.randomUUID().toString();
        user1.setUserId(uuid);
        User saveUser = this.userRepo.save(user1);
        log.info("Completed dao call for save user data :{}");
        return this.modelMapper.map(saveUser, UserDto.class);

    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        log.info("Entering dao call for update user data with userId :{}",userId);
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND +userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());

        User updateUser = this.userRepo.save(user);
        log.info("Completed dao call for update user data with userId :{}",userId);
        return this.modelMapper.map(updateUser, UserDto.class);

    }

    @Override
    public UserDto getUserById(String userId) {
        log.info("Entering dao call for get user data with userId :{}",userId);
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND+ userId));
        log.info("Completed dao call for update user data with userId :{}",userId);
        return this.modelMapper.map(user, UserDto.class);


    }

    @Override
    public  PageableResponse<UserDto> getAllUser(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {

        log.info("Entering dao call for getAll user data :");
        Sort desc = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        PageRequest pageable = PageRequest.of(pageNumber, pageSize,desc);

        Page<User> page = this.userRepo.findAll(pageable);


        PageableResponse<UserDto> response = helper.getPageableResponse(page, UserDto.class);

        log.info("Completed dao call for getAll user data :");
        return response;
    }

    @Override
    public void deleteUser(String userId) {
        log.info("Entering dao call for delete user data with userId :{}",userId);
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND+userId));
        log.info("Completed dao call for delete user data with userId :{}",userId);
        this.userRepo.delete(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        log.info("Entering dao call for search user data with keyword :{}",keyword);
        List<User> users = this.userRepo.findByAboutContaining(keyword);
        List<UserDto> userDtos = users.stream().map((user) -> this.modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        log.info("Completed dao call for search user data with keyword :{}",keyword);
        return userDtos;
    }

    @Override
    public UserDto getByEmail(String email) {
        log.info("Entering dao call for get user data by email with email :{}",email);

        User user = this.userRepo.findByEmail(email).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND + email));
        log.info("Completed dao call for get user data by email with email :{}",email);
        return this.modelMapper.map(user, UserDto.class);

    }
}
