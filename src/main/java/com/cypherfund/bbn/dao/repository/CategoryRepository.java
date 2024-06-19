package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByGameId(long gameId);
}
