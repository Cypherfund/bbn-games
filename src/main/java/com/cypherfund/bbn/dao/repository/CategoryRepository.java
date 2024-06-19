package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}