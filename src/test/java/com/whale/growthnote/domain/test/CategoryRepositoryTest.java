package com.whale.growthnote.domain.test;

import com.whale.growthnote.domain.category.entity.Category;
import com.whale.growthnote.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	void test() {
		Category category = Category.builder()
			.name("name1")
			.build();
		categoryRepository.save(category);
	}
}
