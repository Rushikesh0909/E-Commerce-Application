package com.bikkadIt.dto;

import com.bikkadIt.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min=3,max=15,message = "Invalid Name")
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "Invalid user email")
    private String email;

    @NotBlank(message = "Password is Required")
    private String password;

    @Size(min=4,max = 6,message = "Invalid Gender")
    private String gender;

    @NotBlank(message = "Write somthing")
    private String about;

    @ImageNameValid
    private String imageName;
}
