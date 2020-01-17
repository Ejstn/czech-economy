package com.estn.economy.exchangerate.domain

import com.estn.economy.core.domain.date.DateFactory
import com.estn.economy.exchangerate.data.cnbapi.CNBClient
import com.estn.economy.exchangerate.data.cnbapi.ExchangeRateRootDto
import com.estn.economy.exchangerate.data.cnbapi.toDomain
import com.estn.economy.exchangerate.data.database.ExchangeRateRepository
import com.estn.economy.exchangerate.data.database.toEntity
import com.estn.economy.utility.any
import com.estn.economy.utility.exampleRate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.ResponseEntity
import java.util.*

/**
 * Written by estn on 17.01.2020.
 */
@ExtendWith(MockitoExtension::class)
class ExchangeRateServiceTest {

    @Mock
    lateinit var cnbClient: CNBClient
    @Mock
    lateinit var repository: ExchangeRateRepository
    @Mock
    lateinit var configuration: ExchangeRateConfiguration
    @Mock
    lateinit var dateFactory: DateFactory

    lateinit var service: ExchangeRateService

    @BeforeEach
    fun setUp() {
        service = ExchangeRateService(cnbClient, repository, configuration, dateFactory)
    }

    @Test
    fun `service fetches latest exchange rates`() {
        // given
        given(repository.findAllRatesFromLastDay()).willReturn(listOf(exampleRate.toEntity()))
        // when
        val result = service.fetchLatestExchangeRates()
        // then
        assertThat(result.size).isEqualTo(1)
        assertThat(result.first()).isEqualTo(exampleRate)
    }

    @Test
    fun `service synchronizes exchange rates`() {
        // given
        given(repository.findAllRatesFromLastDay()).willReturn(listOf(exampleRate.toEntity()))
        given(configuration.batchSyncSize).willReturn(1)

        val returnedResponse = ResponseEntity.ok(ExchangeRateRootDto())
        given(cnbClient.fetchExchangeRateForDay(any(Date::class.java))).willReturn(returnedResponse)

        val expectedEntity = returnedResponse.body
                ?.toDomain()
                ?.map { it.toEntity() }
                ?.toList() ?: listOf()
        // when
        service.synchroniseExchangeRates()
        // then
        verify(repository, times(1)).saveAll(ArgumentMatchers.anyCollection())

    }

}