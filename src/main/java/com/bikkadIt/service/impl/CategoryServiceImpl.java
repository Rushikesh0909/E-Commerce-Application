package com.bikkadIt.service.impl;

import com.bikkadIt.constants.AppConstant;
import com.bikkadIt.dto.CategoryDto;
import com.bikkadIt.entity.Category;
import com.bikkadIt.exception.ResourseNotFoundException;
import com.bikkadIt.payloads.PageableResponse;
import com.bikkadIt.payloads.helper;
import com.bikkadIt.repository.CategoryRepo;
import com.bikkadIt.service.CategoryServiceI;
import lombok.experimental.Helper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryServiceI {

    @Autowired
    private CategoryRepo categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        log.info("Entering the Dao call for create the Category");
        String id = UUID.randomUUID().toString();
        categoryDto.setCategoryId(id);
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = this.categoryRepository.save(category);
        log.info("Completed the Dao call for create the Category");
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public PageableResponse getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String direction) {
        log.info("Entering the Dao call for Get All Categories :");
        Sort sort = (direction.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        PageRequest pages = PageRequest.of(pageNumber, pageSize, sort);

        Page<Category> all = this.categoryRepository.findAll(pages);
        PageableResponse<CategoryDto> pageableResponse = helper.getPageableResponse(all, CategoryDto.class);
        log.info("Completed the Dao call for Get All Categories :");
        return pageableResponse;
    }

    @Override
    public CategoryDto getSingleCategory(String categoryId) {
        log.info("Entering the Dao call for Get Single Category With CategoryId :{}",categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND+categoryId));

        CategoryDto dto = modelMapper.map(category, CategoryDto.class);
        log.info("Completed the Dao call for Get All  Category With CategoryId:{}",category);
        return dto;
    }

    @Override
    public void deleteCategory(String categoryId) {
        log.info("Entering the Dao call for Delete The Category With CategoryId :{}",categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND));
        log.info("Completed the Dao call for Delete The Category With CategoryId :{}",categoryId);
        this.categoryRepository.delete(category);

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        log.info("Entering the Dao call for Update The Category With CategoryId :{}",categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND));
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());
        category.setCoverImage(categoryDto.getCoverImage());
        this.categoryRepository.save(category);
        CategoryDto dto = this.modelMapper.map(category, CategoryDto.class);
        log.info("Completed the Dao call for Update The Category With CategoryId :{}",categoryId);
        return dto;
    }
}
