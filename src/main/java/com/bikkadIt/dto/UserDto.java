package com.bikkadIt.dto;

import com.bikkadIt.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.Email;
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

    @Email(message = "please enter valid email id")
    private  String email;

//   @Pattern( regexp = "^(?=.[a-z])(?=.[A-Z])(?=.\\d)(?=.[@#$%^&+=]).*$",
//    message = "password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character")
    private String password;

    @Size(min=4,max = 6,message = "Invalid Gender")
    private String gender;

    @NotBlank(message = "Write something")
    private String about;

    @ImageNameValid
    private String imageName;
}
