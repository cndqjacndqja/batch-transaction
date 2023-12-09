package com.whale.growthnote.domain.category.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whale.growthnote.domain.category.entity.Category;

import java.util.List;

import static com.whale.growthnote.domain.category.entity.QCategory.category;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CategoryRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Category> findContainsName(String name) {
        return queryFactory
                .selectFrom(category)
                .where(category.name.contains(name))
                .fetch();
    }
}
