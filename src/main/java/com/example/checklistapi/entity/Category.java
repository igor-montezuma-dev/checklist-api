package com.example.checklistapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity {

    private Long categoryId;
    private String categoryName;
    private List<ChecklistItemEntity> checklistItems;
}
