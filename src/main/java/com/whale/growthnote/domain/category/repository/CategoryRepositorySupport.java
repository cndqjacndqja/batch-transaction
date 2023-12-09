package com.whale.growthnote.domain.category.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whale.growthnote.domain.category.entity.Category;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.whale.growthnote.domain.category.entity.QCategory.category;

// 이렇게 하면 Repository가 두개로 나뉘어져 써야하기 때문에 불편하다.
// 그래서 다음 커밋에서 CustomRepository 방식이 나오게 된다. (일종의 패턴같은 것이다.)
// https://docs.spring.io/spring-data/jpa/docs/2.1.3.RELEASE/reference/html/#repositories.custom-implementations
@Repository
public class CategoryRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public CategoryRepositorySupport(JPAQueryFactory queryFactory) {
        super(Category.class);
        this.queryFactory = queryFactory;
    }

    public List<Category> findContainsName(String name) {
        return queryFactory.selectFrom(category)
                .where(category.name.contains(name))
                .fetch();
    }
}
