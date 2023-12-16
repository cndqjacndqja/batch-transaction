package com.whale.growthnote.job;

import com.whale.growthnote.domain.category.entity.Category;
import com.whale.growthnote.domain.category.repository.CategoryRepository;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "categoryCreateChunkJobValue")
public class CategoryCreateChunkJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	private final PlatformTransactionManager platformTransactionManager;
	private final CategoryRepository categoryRepository;

	@Bean
	public Job categoryCreateChunkJobValue() {
		return jobBuilderFactory.get("categoryCreateChunkJobValue")
			.start(categoryCreateChunkStep())
			.incrementer(new RunIdIncrementer())
			.build();
	}

	@Bean
	public Step categoryCreateChunkStep() {
		return stepBuilderFactory.get("categoryCreateChunkStep")
			.<Category, Category>chunk(50)
			.reader(new CategoryCreateChunkItemReader(categoryRepository, 0L))
			.writer(categoryCreateChunkWriter())
			.transactionManager(platformTransactionManager)
			.build();
	}

	@Bean
	public ItemWriter<Category> categoryCreateChunkWriter() {
		return categories -> {
			for (Category category : categories) {
				log.info("test");
				log.info(category.getName());
			}
		};
	}
}
