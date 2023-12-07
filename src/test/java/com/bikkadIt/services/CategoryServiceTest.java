package com.bikkadIt.services;

import com.bikkadIt.dto.CategoryDto;
import com.bikkadIt.entity.Category;
import com.bikkadIt.entity.User;
import com.bikkadIt.repository.CategoryRepo;
import com.bikkadIt.service.CategoryServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    private CategoryRepo categoryRepo;

    @Autowired
    private CategoryServiceI categoryServiceI;

    @Autowired
    private ModelMapper modelMapper;

    Category category;
    Category category1;

    @BeforeEach
    public void init(){
        category=Category.builder().title("vivo")
                .description("this is vivo mobile")
                .coverImage("abc.png").build();

        category1=Category.builder().title("oppo")
                .description("this is oppo mobile")
                .coverImage("xyz.png").build();
    }

    @Test
    public void createCategoryTest(){

        Mockito.when(this.categoryRepo.save(Mockito.any())).thenReturn(category);
        CategoryDto category1 = this.categoryServiceI.createCategory(modelMapper.map(category, CategoryDto.class));
        System.out.println(category1.getTitle());
        Assertions.assertNotNull(category1);

    }

    @Test
    public void updateCategoryTest(){
        CategoryDto categoryDto=CategoryDto.builder()
                .title("oppo")
                .description("this is oppo mobile")
                .coverImage("xyz.png").build();

        Mockito.when(this.categoryRepo.findById("categoryId")).thenReturn(Optional.of(category));
        Mockito.when(this.categoryRepo.save(category)).thenReturn(category);

        CategoryDto acctualResult = this.categoryServiceI.updateCategory(categoryDto, "categoryId");
        Assertions.assertNotNull(acctualResult);
        Assertions.assertEquals("oppo",acctualResult.getTitle());
    }

    @Test
    public void getSingleCategoryTest(){

        String categoryId="categoryId";
        Mockito.when(this.categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
        CategoryDto acctualResult = this.categoryServiceI.getSingleCategory(categoryId);
        Assertions.assertNotNull(acctualResult);
        Assertions.assertEquals(category.getTitle(),acctualResult.getTitle());

    }

    @Test
    public void getAllCategoryTest(){

        List<Category> categories = Arrays.asList(category, category1);
        Page<Category> page=new PageImpl<>(categories);

        Mockito.when(this.categoryRepo.findAll((Pageable) Mockito.any())).thenReturn(page);
        Sort.by("title").ascending();

    }

    @Test
    public void deleteCategoryTest(){

        String categoryId="abc";

        Mockito.when(this.categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
        this.categoryServiceI.deleteCategory(categoryId);
        Mockito.verify(categoryRepo,Mockito.times(1)).delete(category);
    }
}
