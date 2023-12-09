package com.whale.growthnote.job;

import com.whale.growthnote.domain.category.entity.Category;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "CategoryCreateChunkJob")
public class CategoryCreateChunkJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory entityManagerFactory;

	@Bean
	public Job job() {
		return jobBuilderFactory.get("categoryChunkJob")
			.incrementer(new RunIdIncrementer())
			.start(categoryCreateChunkStep())
			.build();
	}

	@Bean
	public Step categoryCreateChunkStep() {
		return stepBuilderFactory.get("categoryCreateChunkStep")
			.<Category, Category>chunk(50)
			.reader(categoryCreateChunkReader())
			.writer(categoryCreateChunkWriter())
			.build();
	}

	@Bean
	public JpaPagingItemReader<Category> categoryCreateChunkReader() {
		return new JpaPagingItemReaderBuilder<Category>()
			.name("jpaPagingItemReader")
			.entityManagerFactory(entityManagerFactory)
			.pageSize(50)
			.queryString("SELECT p FROM Category p")
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
