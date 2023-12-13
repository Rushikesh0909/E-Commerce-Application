package com.bikkadIt.dto;

import com.bikkadIt.entity.Category;
import lombok.*;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private  String productId;

    @NotBlank
    @Size(min = 5,max = 50,message = "Product Title Should Be Minimum 5 Character & Maximum 50 Character ")
    private String title;

    @NotBlank
    @Size(min = 5,max = 500,message = "Product  Description Should Be Minimum 5 Character & Maximum 500 Character ")
    private String description;

    @NotNull(message = "Product Price Must Not Be Blank")
    private Double price;

    @NotNull(message = "Product Price Must Not Be Blank")
    private Double discountedPrice;

    @NotNull(message = "Quantity Should Not Be Blank")
    private Integer quantity;

    private Date addedDate;

    private Boolean live;

    private Boolean stock;

    private String image;

    private CategoryDto categories;
}
