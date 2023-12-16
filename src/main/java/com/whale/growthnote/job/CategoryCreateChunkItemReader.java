package com.whale.growthnote.job;

import com.whale.growthnote.domain.category.entity.Category;
import com.whale.growthnote.domain.category.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.batch.item.database.AbstractPagingItemReader;

public class CategoryCreateChunkItemReader extends AbstractPagingItemReader<Category> {

	private final CategoryRepository categoryRepository;
	private Long lastCursorId;

	public CategoryCreateChunkItemReader(CategoryRepository categoryRepository, Long lastCursorId) {
		this.categoryRepository = categoryRepository;
		this.lastCursorId = lastCursorId;
	}

	@Override
	protected void doReadPage() {
		addCategory();
	}

	private void addCategory() {
		if (results == null) {
			results = new ArrayList<>();
		} else {
			results.clear();
		}

		List<Category> categories = categoryRepository.findByCursorIdAndSize(lastCursorId, getPageSize());
		if (!categories.isEmpty()) {
			this.lastCursorId = categories.get(categories.size() - 1).getId();
		}
		results.addAll(categories);
	}

	@Override
	protected void doClose() throws Exception {
		super.doClose();
		this.lastCursorId = 0L;
	}

	@Override
	protected void doJumpToPage(int itemIndex) {
		addCategory();
	}
}
