package com.estn.economy.core.presentation

import com.estn.economy.core.domain.date.DateFormatter
import com.estn.economy.exchangerate.domain.ExchangeRate
import com.estn.economy.exchangerate.domain.FetchExchangeRateUseCase
import com.estn.economy.utility.any
import com.estn.economy.utility.exampleRate
import com.estn.economy.utility.mockLatestRates
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate

/**
 * Written by estn on 16.01.2020.
 */
@Suppress("MemberVisibilityCanBePrivate")
@WebMvcTest(controllers = [EconomyController::class])
class EconomyControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var useCaseFetch: FetchExchangeRateUseCase

    @MockBean
    lateinit var dateFormatter: DateFormatter

    val date = "15.1.2020"

    @Test
    fun `GET root route returns dashboard`() {
        // given
        val expectedRates = listOf(exampleRate)
        val expectedDashboard = EconomyController.EconomyDashboard(date, expectedRates)

        given(dateFormatter.formatDateForFrontEnd(any(LocalDate::class.java)))
                .willReturn(date)

        useCaseFetch.mockLatestRates(expectedRates)
        // when
        // then
        mvc.perform(get("/"))
                .andExpect {
                    status().is2xxSuccessful
                    model().attribute("dashboard", expectedDashboard)
                }
    }

    @Test
    fun `GET that results in internal server error is handled`() {
        // given
        given(useCaseFetch.fetchLatestRates()).willThrow(RuntimeException("whoops"))
        // when
        // then
        mvc.perform(get("/"))
                .andExpect {
                    status().is5xxServerError
                    model().attributeDoesNotExist("dashboard")
                    model().attribute("status", 500)
                    view().name("5xx")
                }
    }

    @Test
    fun `GET kurzy USD returns model with usd currencies only`() {
        // given
        val currencyCode = "USD"
        val expectedRates = listOf(ExchangeRate(LocalDate.now(), currencyCode, "dolar", 1, 22.5, "USA"))

        given(useCaseFetch.fetchByCurrencyOrderByDate(currencyCode)).willReturn(expectedRates)
        // when
        // then
        mvc.perform(get("/kurzy/${currencyCode}"))
                .andExpect {
                    status().is2xxSuccessful
                    model().attribute("rates", expectedRates)
                }
    }

    @Test
    fun `GET kurzy UNKNOWN currency returns 404 model`() {
        // given
        val currencyCode = "UNKNOWN"

        given(useCaseFetch.fetchByCurrencyOrderByDate(currencyCode)).willReturn(listOf())
        // when
        // then
        mvc.perform(get("/kurzy/${currencyCode}"))
                .andExpect {
                    status().is4xxClientError
                    model().attributeDoesNotExist("rates")
                    model().attribute("status", 404)
                    view().name("4xx")
                }
    }



}