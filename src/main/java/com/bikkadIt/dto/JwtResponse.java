package com.bikkadIt.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

    private String jwtToken;

    private  UserDto user;



}
