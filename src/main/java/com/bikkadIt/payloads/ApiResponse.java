package com.bikkadIt.payloads;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {

    private String message;

    private boolean status;

    private HttpStatus httpStatus;
}
