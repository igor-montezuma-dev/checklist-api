package com.example.checklistapi.repository;

import com.example.checklistapi.entity.CategoryEntity;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByName(String name);

    Optional<CategoryEntity> findByGuid(String guid);

}
