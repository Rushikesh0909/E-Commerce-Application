package com.bikkadIt.controller;

import com.bikkadIt.dto.UserDto;
import com.bikkadIt.entity.User;
import com.bikkadIt.payloads.ApiResponse;
import com.bikkadIt.payloads.PageableResponse;
import com.bikkadIt.service.FileService;
import com.bikkadIt.service.UserServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceI userServiceI;

    @MockBean
    private FileService fileService;

    @Autowired
    private ModelMapper modelMapper;

    User user;
    User user1;

    @BeforeEach
    public void init() {
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
                .about("I am backend developer")
                .gender("male")
                .imageName("xyz.png")
                .password("avi123")
                .build();
    }

    private String convertObjectToJsonString(User user)  {
        try {
          return new ObjectMapper().writeValueAsString(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void createUserTest() throws Exception {

        UserDto dto = modelMapper.map(user, UserDto.class);
        Mockito.when(userServiceI.createUser(Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());


    }

    @Test
    public void updateUserTest() throws Exception {

        String userId="abc";
        UserDto dto = modelMapper.map(user, UserDto.class);
        Mockito.when(this.userServiceI.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/users/userId/"+userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());

    }

    @Test
    public void getAllUsersTest() throws Exception {

        UserDto userDto = UserDto.builder().name("Rushi").email("rushi@gmail.com").about("java developer").gender("male").password("123").imageName("rushi.png").build();
        UserDto userDto1 = UserDto.builder().name("Avi").email("avi@gmail.com").about("I am tester").gender("male").password("234").imageName("avi.png").build();
        UserDto userDto2 = UserDto.builder().name("Sahil").email("sa@gmail.com").about("Backend developer").gender("male").password("543").imageName("sahil.png").build();

        PageableResponse<UserDto>pageResponse=new PageableResponse<>();
        pageResponse.setContent(Arrays.asList(userDto,userDto1,userDto2));
        pageResponse.setLastPage(false);
        pageResponse.setPageNumber(100);
        pageResponse.setPageSize(10);
        pageResponse.setTotalElement(1000);
        Mockito.when(userServiceI.getAllUser(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());


    }

    @Test
    public void getSingleUserTest() throws Exception {
       String userId="123";
        UserDto dto = modelMapper.map(user, UserDto.class);
        Mockito.when(userServiceI.getUserById(userId)).thenReturn(dto);

        mockMvc.perform(
               MockMvcRequestBuilders.get("/api/users/userId/"+userId)
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void deleteUserTest() throws Exception {

        String userId="123";
        Mockito.doNothing().when(userServiceI).deleteUser(userId);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/users/userId/"+userId)
        ).andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void getUserByEmailTest() throws Exception {

        String email="rushi@gmail.com";
        UserDto userDto= modelMapper.map(user, UserDto.class);
        Mockito.when(userServiceI.getByEmail(Mockito.anyString())).thenReturn(userDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/email/"+email)
        ).andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void searchUserTest() throws Exception {

        UserDto userDto = UserDto.builder().name("Rushi").email("rushi@gmail.com").about("java developer").gender("male").password("123").imageName("rushi.png").build();
        UserDto userDto1 = UserDto.builder().name("Avi").email("avi@gmail.com").about("I am tester").gender("male").password("234").imageName("avi.png").build();
        UserDto userDto2 = UserDto.builder().name("Sahil").email("sa@gmail.com").about("Backend developer").gender("male").password("543").imageName("sahil.png").build();

        String keyword="Rushi";

        Mockito.when(userServiceI.searchUser(Mockito.anyString())).thenReturn(Arrays.asList(userDto,userDto1,userDto2));

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/keyword/"+keyword)
        ).andDo(print()).andExpect(status().isOk());


    }




   }
