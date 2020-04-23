package com.marvic.popstats.service

import com.marvic.popstats.data.CoreBasedStatisticalAreaRepository
import com.marvic.popstats.domain.CoreBasedStatisticalArea
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `FindAll should call the repository and return a response`() {
        val expectedResult = listOf<CoreBasedStatisticalArea>()
        every { repository.findAll() } returns expectedResult

        val result = classUnderTest.findAll()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `FindAllBy should call the repository and return a list`() {
        val codeList = listOf("12000")
        val titleList = listOf<String>()
        val expectedResult = listOf<CoreBasedStatisticalArea>()
        every { repository.findAllByTitleInOrCodeIn(titleList, codeList) } returns expectedResult

        val result = classUnderTest.findAllBy(codeList, titleList)

        assertThat(result).isEqualTo(expectedResult)
    }
}