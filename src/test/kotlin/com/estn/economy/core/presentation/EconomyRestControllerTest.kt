package com.estn.economy.core.presentation

import com.estn.economy.core.presentation.controller.EconomyRestController
import com.estn.economy.exchangerate.domain.FetchExchangeRateUseCase
import com.estn.economy.utility.exampleRate
import com.estn.economy.utility.mockLatestRates
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


/**
 * Written by estn on 16.01.2020.
 */
@Suppress("MemberVisibilityCanBePrivate")
@WebMvcTest(controllers = [EconomyRestController::class])
@AutoConfigureRestDocs(outputDir = "target/snippets", uriHost = "ekonastenka.cz", uriScheme = "https", uriPort = 443)
class EconomyRestControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var mapper: ObjectMapper

    @MockBean
    lateinit var useCaseFetch: FetchExchangeRateUseCase

    @Test
    fun `GET exchangerate returns rates from service`() {
        // given
        val mockRates = listOf(exampleRate)
        useCaseFetch.mockLatestRates(mockRates)

        val expectedEntity = EconomyRestController.ExchangeRatesResponse(mockRates)
        val expectedJsonResponse = mapper.writeValueAsString(expectedEntity)
        // when
        // then
        mvc.perform(get("/api/v1/exchangerate")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(   status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJsonResponse))
                .andDo(document("exchangeRate"))

    }

}