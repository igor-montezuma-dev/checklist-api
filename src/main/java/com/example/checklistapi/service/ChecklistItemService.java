package com.example.checklistapi.service;

import com.example.checklistapi.entity.CategoryEntity;
import com.example.checklistapi.entity.ChecklistItemEntity;
import com.example.checklistapi.exceptions.ResourceNotFoundException;
import com.example.checklistapi.repository.CategoryRepository;
import com.example.checklistapi.repository.ChecklistItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.time.LocalDate;
import java.util.UUID;


@Slf4j
@Service
public class ChecklistItemService {

    private final ChecklistItemRepository checklistItemRepository;
    private final CategoryRepository categoryRepository;

    public ChecklistItemService(ChecklistItemRepository checklistItemRepository, CategoryRepository categoryRepository) {
        this.checklistItemRepository = checklistItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public ChecklistItemEntity addNewChecklistItem(
            Boolean isComplete,
            String description,
            LocalDate deadline,
            String categoryGuid
    ) {
        this.validateChecklistItemData(isComplete, description, deadline, categoryGuid);
        CategoryEntity retrievedCategory = this.categoryRepository.findByGuid(categoryGuid)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));

        ChecklistItemEntity checklistItem = new ChecklistItemEntity();
        checklistItem.setGuid(UUID.randomUUID().toString());
        checklistItem.setDescription(description);
        checklistItem.setDeadline(deadline);
        checklistItem.setPostedDate(LocalDate.now());
        checklistItem.setIsComplete(isComplete);
        checklistItem.setCategory(retrievedCategory);

        log.debug("Adding new checklist item: {}", checklistItem);
        return checklistItemRepository.save(checklistItem);
    }

    public Iterable<ChecklistItemEntity> getAllChecklistItems() {
        return checklistItemRepository.findAll();
    }

    public ChecklistItemEntity getChecklistItemByGuid(String guid) {
        if (!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Guid cannot be null or empty.");
        }
        return this.checklistItemRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist item not found."));
    }

    public ChecklistItemEntity updateChecklistItem(
            String guid,
            String description,
            Boolean isComplete,
            LocalDate deadline,
            String categoryGuid) {

        if (!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Guid cannot be null or empty.");
        }
        ChecklistItemEntity retrievedItem = this.checklistItemRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist item not found."));
        if (StringUtils.hasText(description)) {
            retrievedItem.setDescription(description);
        }
        if (isComplete != null) {
            retrievedItem.setIsComplete(isComplete);
        }
        if (deadline != null) {
            retrievedItem.setDeadline(deadline);
        }
        if (StringUtils.hasText(categoryGuid)) {
            CategoryEntity retrievedCategory = this.categoryRepository.findByGuid(categoryGuid)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
            retrievedItem.setCategory(retrievedCategory);
        }

        log.debug("Updating checklist item: {}", retrievedItem.toString());
        return this.checklistItemRepository.save(retrievedItem);
    }

    public void deleteChecklistItem(String guid) {
        if (!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Guid cannot be null or empty.");
        }
        ChecklistItemEntity retrievedItem = this.checklistItemRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist item not found."));

        log.debug("Deleting checklist item: {}", guid);

        this.checklistItemRepository.delete(retrievedItem);
    }

    private void validateChecklistItemData(
            Boolean isComplete,
            String description,
            LocalDate deadline,
            String categoryGuid) {

        if (!StringUtils.hasText(description)) {
            throw new IllegalArgumentException("Checklist item must have a description.");
        }
        if (isComplete == null) {
            throw new IllegalArgumentException("Checklist item must have a flag indicating if it is complete or not.");
        }
        if (deadline == null) {
            throw new IllegalArgumentException("Checklist item must have a deadline.");
        }
        if (categoryGuid == null) {
            throw new IllegalArgumentException("Checklist item must have a category.");
        }
    }


}
