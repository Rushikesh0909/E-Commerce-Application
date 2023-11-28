package com.bikkadIt.service;

import com.bikkadIt.dto.CategoryDto;
import com.bikkadIt.payloads.PageableResponse;

public interface CategoryServiceI {

    //create
    CategoryDto createCategory(CategoryDto categoryDto);

    // getall
    PageableResponse getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String direction);

    //getById
    CategoryDto getSingleCategory(String categoryId);
    //delete
    void deleteCategory(String categoryId);
    //update
    CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);

}
