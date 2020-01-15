package com.estn.economy.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

/**
 * Written by ctn.
 */
class DateFactoryTest {

    val factory = DateFactory()

    @Test
    fun `factory generates correct number of dates`() {
        // given
        // when
        val result = factory.generateDaysGoingBackIncludingToday(5)
        // then
        assertThat(result.size).isEqualTo(6)
    }

    @Test
    fun `factory generates dates with correct offset - one day`() {
        // given
        // when
        val result = factory.generateDaysGoingBackIncludingToday(1)
                .toList()
                .sortedByDescending { it.time }

        val today = result.first()
        val yesterday = result[1]
        // then
        val timedifference = today.time - yesterday.time
        assertThat(timedifference).isEqualTo(TimeUnit.DAYS.toMillis(1))
    }
}