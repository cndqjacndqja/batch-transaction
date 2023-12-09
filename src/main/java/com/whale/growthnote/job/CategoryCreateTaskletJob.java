package com.whale.growthnote.job;

import static com.whale.growthnote.job.CategoryCreateTaskletJob.JOB_NAME;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = JOB_NAME)
public class CategoryCreateTaskletJob {

	public static final String JOB_NAME = "createTaskletJob";

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;


	@Bean(JOB_NAME)
	public Job createTaskletJob() {
		return jobBuilderFactory.get(JOB_NAME)
			.start(simpleStep1())
			.incrementer(new RunIdIncrementer())
			.build();
	}

	@Bean
	public Step simpleStep1() {
		return stepBuilderFactory.get("simpleStep1")
			.tasklet((contribution, chunkContext) -> {
				log.info(contribution.toString());
				log.info(chunkContext.toString());
				log.info(">>>>> This is Step1");
				return RepeatStatus.FINISHED;
			})
			.build();
	}
}
