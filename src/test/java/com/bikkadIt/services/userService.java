package com.bikkadIt.services;

import com.bikkadIt.dto.UserDto;
import com.bikkadIt.entity.User;
import com.bikkadIt.payloads.PageableResponse;
import com.bikkadIt.repository.UserRepo;
import com.bikkadIt.service.UserServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class userService {

    @MockBean
    private UserRepo userRepo;

    @Autowired
    private UserServiceI userService;

    @Autowired
    private ModelMapper modelMapper;

    User user;
    User user1;
    @BeforeEach
    public void init(){
         user = User.builder()
                .name("Rushi")
                .email("rushikesh@gmail.com")
                .about("I am java developer")
                .gender("male")
                .imageName("abc.png")
                .password("rushi123")
                .build();

       user1 = User.builder()
                .name("Avi")
                .email("avi@gmail.com")
                .about("I am java Tester")
                .gender("male")
                .imageName("xyz.png")
                .password("avi123")
                .build();
    }
    // create user test case

    @Test
    public void createUserTest(){

        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);
        UserDto userDto = userService.createUser(modelMapper.map(user, UserDto.class));
        System.out.println(userDto.getName());
        Assertions.assertNotNull(userDto);

    }

    // update user test case
    @Test
    public void updateUserTest(){

       UserDto userDto = UserDto.builder()
                .name("Avi")
               .email("avi@gmail.com")
                .about("I am java Tester")
                .gender("male")
                .imageName("ab.png")
                .password("avi123")
                .build();

        Mockito.when(this.userRepo.findById("userId")).thenReturn(Optional.of(user));
        Mockito.when(this.userRepo.save(user)).thenReturn(user);

        UserDto actualResult = this.userService.updateUser(userDto, "userId");
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals("Avi",actualResult.getName());

    }

    // get User By Id test case
    @Test
    public void getUserByIdTest(){

        String userId="xyz";

       Mockito.when(this.userRepo.findById(userId)).thenReturn(Optional.of(user));
        UserDto actualResult = this.userService.getUserById(userId);
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(user.getName(),actualResult.getName());

    }

    @Test
    public void getByEmailTest(){

        String email="rushi@gmail.com";
        Mockito.when(this.userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        UserDto acctualResult = this.userService.getByEmail(email);
        Assertions.assertNotNull(acctualResult);
        Assertions.assertEquals(user.getEmail(),acctualResult.getEmail());

    }

    @Test
    public void getAllUserTest(){

        Mockito.when(this.userRepo.findAll()).thenReturn(List.of(user));
//        PageableResponse<UserDto> actualResult = this.userService.getAllUser();

    }
    @Test
    public void searchUserTest(){

        String keyword="java";
        Mockito.when(this.userRepo.findByAboutContaining(keyword)).thenReturn(List.of(user,user1));
        List<UserDto> acctualResult = this.userService.searchUser(keyword);
        Assertions.assertEquals(2,acctualResult.size());

    }

    @Test
    public void deleteUserTest(){

        String userId="xyz";
        Mockito.when(this.userRepo.findById(userId)).thenReturn(Optional.of(user));
        this.userService.deleteUser(userId);
        Mockito.verify(userRepo,Mockito.times(1)).delete(user);
    }
}
