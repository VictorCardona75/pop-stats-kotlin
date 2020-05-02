package com.marvic.popstats.service

import com.marvic.popstats.data.CoreBasedStatisticalAreaRepository
import com.marvic.popstats.domain.CoreBasedStatisticalArea
import com.marvic.popstats.domain.StatisticalAreaType
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.Called
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.inValues
import org.springframework.data.mongodb.core.query.isEqualTo

internal class CoreBasedStatisticalAreaServiceTest {
    val repository = mockk<CoreBasedStatisticalAreaRepository>()
    val classUnderTest: CoreBasedStatisticalAreaService = CoreBasedStatisticalAreaService(repository)

    @BeforeEach
    fun setup() = clearAllMocks()

    @Test
    fun `FindByCode should call the repository and return a response`() {
        val input = "12000"
        val expectedResult: CoreBasedStatisticalArea? = null
        every { repository.findByCode(input) } returns expectedResult

        val result: CoreBasedStatisticalArea? = classUnderTest.findByCode(input)

        result.shouldBeSameInstanceAs(expectedResult)
    }

    @Test
    fun `FindAll should call the repository and return a response`() {
        val expectedResult = listOf<CoreBasedStatisticalArea>()
        every { repository.findAll() } returns expectedResult

        val result = classUnderTest.findAll()

        result.shouldBeSameInstanceAs(expectedResult)
    }

    @Nested
    inner class FindAllBy {
        @Test
        fun `should call findAll if no filters are passed`() {
            val input = Filters(listOf(), listOf(), null)
            val expectedResult = listOf<CoreBasedStatisticalArea>()
            every { repository.findAll() } returns expectedResult

            val result = classUnderTest.findAllBy(input)

            result.shouldBeSameInstanceAs(expectedResult)
            verify { repository.findAll() }
            verify(exactly = 0) { repository.findBAllyQuery(any()) }
        }

        @Test
        fun `should add criterion for codes`() {
            val input = Filters(listOf("12000"), listOf(), null)
            val expectedResult = listOf<CoreBasedStatisticalArea>()
            val slot = slot<Query>()
            every { repository.findBAllyQuery(query = capture(slot)) } returns expectedResult

            val result = classUnderTest.findAllBy(input)

            result.shouldBeSameInstanceAs(expectedResult)
            verify { repository.findBAllyQuery(query = Query(CoreBasedStatisticalArea::code inValues input.codes)) }
        }

        @Test
        fun `should add criterion for titles`() {
            val input = Filters(listOf(), listOf("St. Louis, MO-IL"), null)
            val expectedResult = listOf<CoreBasedStatisticalArea>()
            val slot = slot<Query>()
            every { repository.findBAllyQuery(query = capture(slot)) } returns expectedResult

            val result = classUnderTest.findAllBy(input)

            result.shouldBeSameInstanceAs(expectedResult)
            verify { repository.findBAllyQuery(query = Query(CoreBasedStatisticalArea::title inValues input.titles)) }
        }

        @Test
        fun `should add criterion for type`() {
            val input = Filters(listOf(), listOf(), StatisticalAreaType.METROPOLITAN)
            val expectedResult = listOf<CoreBasedStatisticalArea>()
            val slot = slot<Query>()
            every { repository.findBAllyQuery(query = capture(slot)) } returns expectedResult

            val result = classUnderTest.findAllBy(input)

            result.shouldBeSameInstanceAs(expectedResult)
            verify { repository.findBAllyQuery(query = Query.query(CoreBasedStatisticalArea::areaType isEqualTo input.type)) }
        }

        @Test
        fun `should add criteria for title and type`() {
            val input = Filters(listOf(), listOf("St. Louis, MO-IL"), StatisticalAreaType.METROPOLITAN)
            val expectedResult = listOf<CoreBasedStatisticalArea>()
            val slot = slot<Query>()
            every { repository.findBAllyQuery(query = capture(slot)) } returns expectedResult

            val result = classUnderTest.findAllBy(input)

            result.shouldBeSameInstanceAs(expectedResult)
            verify {
                repository.findBAllyQuery(
                    query = Query.query(
                        Criteria().andOperator(
                            CoreBasedStatisticalArea::title inValues input.titles,
                            CoreBasedStatisticalArea::areaType isEqualTo input.type
                        )
                    )
                )
            }
        }
    }
}