package com.estn.economy.core.domain.date

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate

/**
 * Written by estn on 16.01.2020.
 */
@ExtendWith(MockitoExtension::class)
class DateFormatterTest {

    val frontEndFormat = "d.M.yyyy"
    val cnbFormat = "dd.MM.yyyy"

    @Mock
    lateinit var config: DateFormatConfiguration

    lateinit var formatter: DateFormatter

    @BeforeEach
    fun setUp() {
        given(config.frontEndExchangeRateDateFormat).willReturn(frontEndFormat)
        given(config.cnbExchangeRateApiDateFormat).willReturn(cnbFormat)
        formatter = DateFormatter(config)
    }

    @Test
    fun `test frontend format configuration`() {
        // given
        val input = LocalDate.of(2020,1,17) // 17.1.2020
        // when
        val result = formatter.formatDateForFrontEnd(input)
        // then
        assertThat(result).isEqualTo("17.1.2020")
    }

    @Test
    fun `test cnb format configuration`() {
        // given
        val input = LocalDate.of(2020,1,17) // 17.1.2020
        // when
        val result = formatter.formatDateForCnbApi(input)
        // then
        assertThat(result).isEqualTo("17.01.2020")
    }

    @Test
    fun `2015-02-06 is formatted correctly for cnb api`() {
        // given
        val input = LocalDate.of(2015,2,6) // 6.2.2015
        // when
        val result = formatter.formatDateForCnbApi(input)
        // then
        assertThat(result).isEqualTo("06.02.2015")
    }
}