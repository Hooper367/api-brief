package com.apibrief3.repository;

import com.apibrief3.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category, Integer> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByName(String name);
}
