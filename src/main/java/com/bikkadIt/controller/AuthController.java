package com.bikkadIt.controller;

import com.bikkadIt.dto.JwtRequest;
import com.bikkadIt.dto.JwtResponse;
import com.bikkadIt.dto.UserDto;
import com.bikkadIt.exception.BadRequest;
import com.bikkadIt.security.JwtHelper;
import com.bikkadIt.service.UserServiceI;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private JwtHelper helper;

    @GetMapping("/current")
    public ResponseEntity<UserDetails>getCurrentUser(Principal principal){
        String name = principal.getName();
        return  new ResponseEntity<>(userDetailsService.loadUserByUsername(name), HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
        this.doAuthenticate(request.getEmail(),request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        UserDto userDto=modelMapper.map(userDetails,UserDto.class);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .user(userDto)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private  void doAuthenticate(String email,String password){

        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(email,password);
        try {
            manager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){

            throw new BadRequest("Invalid username or password");
        }
    }
}
