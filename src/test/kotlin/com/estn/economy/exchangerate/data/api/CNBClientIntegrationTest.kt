package com.estn.economy.exchangerate.data.api

import com.estn.economy.TestProfile
import com.estn.economy.core.data.api.CNBClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import java.time.LocalDate

/**
 * Written by estn on 17.01.2020.
 */
@SpringBootTest
@Disabled()
@TestProfile
class CNBClientIntegrationTest {

    @Autowired
    lateinit var client: CNBClient

    @RepeatedTest(value = 10)
    @Test
    fun `client fetches and parses exchange rates for given day`() {
        // given
        // when
        val requestedDate = LocalDate.of(2015, 2, 6)
        val result = client.fetchExchangeRateForDay(requestedDate)
        // then
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.date).isEqualTo(requestedDate)
    }

    @RepeatedTest(value = 10)
    @Test
    fun `client fetches and parses exchange rates for another given day`() {
        // given
        // when
        val requestedDate = LocalDate.of(2020, 1, 20)
        val result = client.fetchExchangeRateForDay(requestedDate)
        // then
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.date).isEqualTo(requestedDate)
    }


}