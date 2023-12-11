package com.bikkadIt.dto;

import com.bikkadIt.entity.Product;
import com.bikkadIt.validate.ImageNameValid;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;

    @NotBlank
    @Size(min = 2,max = 60,message = "Category Title Must Be Min 2 Character & Max 60 Character")
    private String title;

    @NotBlank
    @Size(min = 5,max = 500,message = "Category Description Must Be Min 5 Character & Max 500 Character")
    private String description;

    @ImageNameValid(message = "Image Name Must Not  Be Blank")
    private String coverImage;


}
