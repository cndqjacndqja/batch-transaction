package com.whale.growthnote.domain.job.service

import com.whale.growthnote.GrowthNoteApplication
import com.whale.growthnote.domain.category.entity.Category
import com.whale.growthnote.domain.category.repository.CategoryRepository
import com.whale.growthnote.domain.category.service.request.CreateCategoryRequest
import com.whale.growthnote.domain.job.entity.Job
import com.whale.growthnote.domain.job.repository.JobRepository
import com.whale.growthnote.domain.job.service.request.CreateJobRequest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.IllegalArgumentException

@SpringBootTest(classes = [GrowthNoteApplication::class])
class JobServiceTest(
    @Autowired private val jobService: JobService,
    @Autowired private val jobRepository: JobRepository,
    @Autowired private val categoryRepository: CategoryRepository
) : DescribeSpec({


    describe(".create") {
        context("올바른 값을 입력했을 시") {
            it("job을 생성한 후, 생성한 객체의 id를 반환한다.") {
                val category = Category.builder()
                    .name("name")
                    .build();
                val categoryId = categoryRepository.save(category)
                    .id

                val request = CreateJobRequest("name", categoryId)
                val savedId = jobService.create(request)
                savedId.shouldNotBeNull()
            }
        }

        context("존재하지 않는 categoryId 값을 입력했을 시") {
            it("예외가 발생한다.") {
                val request = CreateJobRequest("name", 0L)
                val result = assertThrows<IllegalArgumentException> {
                    jobService.create(request)
                }
                result.message.shouldBe("존재하지 않는 categoryId 입니다.")
            }
        }
    }

    describe(".complete") {
        context("올바른 jobId를 입력했을 시") {
            it("job의 complete 값이 true로 변환된다.") {
                val category = Category.builder()
                    .name("name")
                    .build()
                val job = Job.builder()
                    .name("name")
                    .category(category)
                    .build()
                categoryRepository.save(category)
                val savedJob = jobRepository.save(job)
                jobService.complete(savedJob.id)
                val result = jobRepository.findById(savedJob.id).get()

                result.isComplete.shouldBe(true)
            }
        }

        context("이미 완료된 jobId를 입력했을 시") {
            it("예외가 발생한다.") {
                val category = Category.builder()
                    .name("name")
                    .build()
                val job = Job.builder()
                    .name("name")
                    .complete(true)
                    .category(category)
                    .build()
                categoryRepository.save(category)
                val savedJob = jobRepository.save(job)

                val result = assertThrows<IllegalArgumentException> {
                    jobService.complete(savedJob.id)
                }
                result.message.shouldBe("이미 완료된 job 입니다.")
            }
        }

        context("존재하지 않는 jobId를 입력했을 시") {
            it("예외가 발생한다.") {
                val result = assertThrows<IllegalArgumentException> {
                    jobService.complete(0L)
                }
                result.message.shouldBe("존재하지 않는 jobId 입니다.")
            }
        }
    }
})
