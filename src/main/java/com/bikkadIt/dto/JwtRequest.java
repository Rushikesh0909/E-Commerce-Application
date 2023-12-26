package com.bikkadIt.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtRequest {

    private String email;

    private String password;

}
