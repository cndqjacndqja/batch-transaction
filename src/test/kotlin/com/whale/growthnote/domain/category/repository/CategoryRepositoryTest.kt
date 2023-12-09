package com.whale.growthnote.domain.category.repository

import com.whale.growthnote.GrowthNoteApplication
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import


@SpringBootTest
class CategoryRepositoryTest(
    @Autowired val categoryRepository: CategoryRepository
) : DescribeSpec() {

    init {
        describe("describe") {
            it("it") {
                categoryRepository.findContainsName("")
            }
        }
    }
}


