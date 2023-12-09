package com.whale.growthnote.domain.category.service

import com.whale.growthnote.GrowthNoteApplication
import com.whale.growthnote.domain.category.service.request.CreateCategoryRequest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.IllegalArgumentException

@SpringBootTest(classes = [GrowthNoteApplication::class])
class CategoryServiceTest(
    @Autowired private val categoryService: CategoryService
) : DescribeSpec({

    describe(".create") {
        context("올바른 값을 입력했을 시") {
            it("category를 생성한 후, 생성한 객체의 id를 반환한다.") {
                val request = CreateCategoryRequest("name")
                val savedId = categoryService.create(request)
                savedId.shouldNotBeNull()
            }
        }

        context("올바른 값을 입력하지 않았을 시") {
            it("예외가 발생한다.") {
                val request = CreateCategoryRequest(null)
                val result = assertThrows<IllegalArgumentException> {
                    categoryService.create(request)
                }
                result.message.shouldBe("카테고리 이름이 비어있습니다.")
            }
        }
    }
})
