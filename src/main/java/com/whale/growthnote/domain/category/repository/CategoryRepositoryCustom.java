package com.whale.growthnote.domain.category.repository;

import com.whale.growthnote.domain.category.entity.Category;

import java.util.List;

public interface CategoryRepositoryCustom {

    List<Category> findContainsName(String name);

    List<Category> findByCursorIdAndSize(Long cursorId, int size);
}
