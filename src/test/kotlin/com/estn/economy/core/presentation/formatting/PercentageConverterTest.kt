package com.estn.economy.core.presentation.formatting

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PercentageConverterTest {

    val converter = PercentageConverter()

    @Test
    fun `2 point 0 is formatted as 2%`() {
        // given
        // when
        val result = converter.convert(Percentage(2.0))
        // then
        assertThat(result).isEqualTo("2%")
    }

    @Test
    fun `0 point 5 is formatted as 0 point 5%`() {
        // given
        // when
        val result = converter.convert(Percentage(0.5))
        // then
        assertThat(result).isEqualTo("0.5%")
    }

    @Test
    fun `100 is formatted as 100%`() {
        // given
        // when
        val result = converter.convert(Percentage(100.0))
        // then
        assertThat(result).isEqualTo("100%")
    }
}