package com.example.checklistapi.repository;

import com.example.checklistapi.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    Optional<Category> findByCategoryName(String categoryName);

    Optional<Category> findByGuid(String guid);
}
