package com.estn.economy.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CalculateIncrementalValueChangesUseCaseTest {

    val useCase = CalculateIncrementalValueChangesUseCase()

    @BeforeEach
    fun setUp() {

    }

    @Test
    fun `use case correctly calculates incremental percentage changes`() {
        // given
        val gdp = listOf(
                InputData(2015, 100, 1),
                InputData(2016, 105, 2),
                InputData(2017, 120, 3),
                InputData(2018, 100, 4))
        // when
        val result = useCase.calculatePercentageChanges(gdp)
        // then
        assertThat(result.size).isEqualTo(3)
        assertThat(result.first().order).isEqualTo(2016)
        assertThat(result.first().value).isCloseTo(5.0, Offset.offset(0.1))
        assertThat(result.first().dataPoint).isEqualTo(2)

        assertThat(result[1]).isEqualTo(OutputPercentageData(2017,((120.toDouble() / 105.0) - 1) * 100, 3))

        assertThat(result[2].order).isEqualTo(2018)
        assertThat(result[2].value).isCloseTo(-16.66, Offset.offset(0.1))
        assertThat(result[2].dataPoint).isEqualTo(4)
    }
}