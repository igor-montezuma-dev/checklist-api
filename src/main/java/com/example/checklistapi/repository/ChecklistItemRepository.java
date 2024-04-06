package com.example.checklistapi.repository;

import com.example.checklistapi.entity.ChecklistItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;


public interface ChecklistItemRepository extends PagingAndSortingRepository<ChecklistItemEntity, Long> {

    Optional<ChecklistItemEntity> findByGuid(String guid);
    Optional<ChecklistItemEntity> findByDescriptionAndIsComplete(String description, boolean isComplete);
}
