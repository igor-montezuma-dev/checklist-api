package com.example.checklistapi.service;
import com.example.checklistapi.entity.CategoryEntity;
import com.example.checklistapi.exceptions.ResourceNotFoundException;
import com.example.checklistapi.repository.CategoryRepository;
import com.example.checklistapi.repository.ChecklistItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service

public class CategoryService {

    private ChecklistItemRepository checklistItemRepository;
    private CategoryRepository categoryRepository;
    public CategoryService(ChecklistItemRepository checklistItemRepository, CategoryRepository categoryRepository) {
        this.checklistItemRepository = checklistItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity addNewCategory(String categoryName) {
        if (!StringUtils.hasText(categoryName)) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        CategoryEntity newCategory = new CategoryEntity();
        newCategory.setGuid(java.util.UUID.randomUUID().toString());
        newCategory.setCategoryName(categoryName);

        log.debug("Adding new category with name [name = {} ]", categoryName);
        return this.categoryRepository.save(newCategory);
    }

    public CategoryEntity updateCategory(String guid, String categoryName) {
        if (!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Category guid cannot be empty");
        }
        if (!StringUtils.hasText(categoryName)) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        CategoryEntity retrievedCategory = this.categoryRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        retrievedCategory.setCategoryName(categoryName);
        log.debug("Updating category with guid [guid = {}, name = {}]", guid, categoryName);
        return this.categoryRepository.save(retrievedCategory);
    }
}
