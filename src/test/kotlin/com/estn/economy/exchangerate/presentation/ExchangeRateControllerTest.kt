package com.estn.economy.exchangerate.presentation

import com.estn.economy.exchangerate.domain.ExchangeRate
import com.estn.economy.exchangerate.domain.FetchExchangeRateUseCase
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate

@WebMvcTest(controllers = [ExchangeRateController::class])
class ExchangeRateControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var useCaseFetch: FetchExchangeRateUseCase

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