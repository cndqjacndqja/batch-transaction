package com.whale.growthnote.domain.category.service;

import com.whale.growthnote.domain.category.entity.Category;
import com.whale.growthnote.domain.category.repository.CategoryRepository;
import com.whale.growthnote.domain.category.service.request.CreateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Long create(CreateCategoryRequest createCategoryRequest) {
        if (createCategoryRequest.getName() == null) {
            throw new IllegalArgumentException("카테고리 이름이 비어있습니다.");
        }
        Category savedCategory = categoryRepository.save(createCategoryRequest.createCategory());
        return savedCategory.getId();
    }
}
