package com.estn.economy.exchangerate.domain

import com.estn.economy.exchangerate.data.cnbapi.*
import com.estn.economy.exchangerate.data.database.ExchangeRateRepository
import com.estn.economy.exchangerate.data.database.toEntity
import com.estn.economy.utility.exampleRate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.ResponseEntity
import java.time.LocalDate

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

    lateinit var service: ExchangeRateService

    @BeforeEach
    fun setUp() {
        service = ExchangeRateService(cnbClient, repository, configuration)
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
    fun `service synchronizes current rates`() {
        // given
        val returnedDto = ExchangeRateRootDto()

        with(returnedDto) {
            bankName = "CNB"
            date = LocalDate.now()
            order = 10
            val exchangeRateTableDto = ExchangeRateTableDto()
            with(exchangeRateTableDto) {
                type = "kurz"
                val rate = ExchangeRateDto()
                with(rate) {
                    currencyCode = "USD"
                    currencyName = "dolar"
                    amount = 1
                    this.rate = 22.5
                    country = "USA"

                }
                rates = listOf(rate)
            }
            exchangeRatesTableDto = exchangeRateTableDto
        }
        val expectedSaveEntity = returnedDto.toDomain()
                .map { it.toEntity() }
                .first()

        given(cnbClient.fetchExchangeRateForDay(LocalDate.now())).willReturn(ResponseEntity.ok(returnedDto))
        // when
        service.synchroniseCurrentRate()
        // then
        verify(cnbClient, times(1)).fetchExchangeRateForDay(LocalDate.now())
        verify(repository, times(1)).save(expectedSaveEntity)

    }


}