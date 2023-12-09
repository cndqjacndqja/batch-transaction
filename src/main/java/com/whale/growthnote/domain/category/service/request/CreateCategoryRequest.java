package com.whale.growthnote.domain.category.service.request;

import com.whale.growthnote.domain.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {

    private String name;

    public Category createCategory() {
        return new Category(name);
    }
}
