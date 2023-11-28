package com.bikkadIt.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Category {

    @Id
    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "category_title")
    private String title;

    @Column(name = "category_description")
    private String description;

    @Column(name = "catergory_image")
    private String coverImage;
}
