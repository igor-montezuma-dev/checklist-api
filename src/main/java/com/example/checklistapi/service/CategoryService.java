package com.example.checklistapi.service;

import com.example.checklistapi.entity.CategoryEntity;
import com.example.checklistapi.entity.ChecklistItemEntity;
import com.example.checklistapi.exceptions.CategoryServiceException;
import com.example.checklistapi.exceptions.ResourceNotFoundException;
import com.example.checklistapi.repository.CategoryRepository;
import com.example.checklistapi.repository.ChecklistItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class CategoryService {
    private final ChecklistItemRepository checklistItemRepository;
    private final CategoryRepository categoryRepository;

    public CategoryService(ChecklistItemRepository checklistItemRepository, CategoryRepository categoryRepository) {
        this.checklistItemRepository = checklistItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity addNewCategory(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        CategoryEntity newCategory = new CategoryEntity();
        newCategory.setGuid(java.util.UUID.randomUUID().toString());
        newCategory.setName(name);

        log.debug("Adding new category with name [name = {} ]", name);
        return this.categoryRepository.save(newCategory);
    }

    public CategoryEntity findCategoryByGuid(String guid) {
        if (!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Category guid cannot be empty");
        }
        return this.categoryRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    public Iterable<CategoryEntity> findAllCategories() {
        return this.categoryRepository.findAll();
    }

    public CategoryEntity updateCategory(String guid, String name) {
        if (!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Category guid cannot be empty");
        }
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        CategoryEntity retrievedCategory = this.categoryRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        retrievedCategory.setName(name);
        log.debug("Updating category with guid [guid = {}, name = {}]", guid, name);
        return this.categoryRepository.save(retrievedCategory);
    }


    public void deleteCategory(String guid) {
        if (!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Category guid cannot be empty");
        }
        CategoryEntity retrievedCategory = this.categoryRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        log.debug("Deleting category with guid [guid = {}]", guid);

        List<ChecklistItemEntity> checklistItems = this.checklistItemRepository.findByCategoryGuid(guid);
        if (!CollectionUtils.isEmpty(checklistItems)) {

            throw new CategoryServiceException("Cannot delete category with checklist items");
        }
        this.categoryRepository.delete(retrievedCategory);
    }
}
