package com.estn.economy

import com.estn.economy.exchangerate.ExchangeRateService
import com.estn.economy.utility.exampleRate
import com.estn.economy.utility.mockLatestRates
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


/**
 * Written by estn on 16.01.2020.
 */
@Suppress("MemberVisibilityCanBePrivate")
@WebMvcTest(controllers = [EconomyRestController::class])
class EconomyRestControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var mapper: ObjectMapper

    @MockBean
    lateinit var service: ExchangeRateService

    @Test
    fun `GET exchangerate returns rates from service`() {
        // given
        val mockRates = listOf(exampleRate)
        service.mockLatestRates(mockRates)

        val expectedEntity = ExchangeRatesResponse(mockRates)
        val expectedJsonResponse = mapper.writeValueAsString(expectedEntity)
        // when
        // then
        mvc.perform(get("/api/exchangerate")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect {
                    status().is2xxSuccessful
                    content().contentType(MediaType.APPLICATION_JSON)
                    content().json(expectedJsonResponse)
                }
    }

}