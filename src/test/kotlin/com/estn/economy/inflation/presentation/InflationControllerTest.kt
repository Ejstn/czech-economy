package com.estn.economy.inflation.presentation

import com.estn.economy.core.presentation.Breadcrumbs
import com.estn.economy.core.presentation.Home
import com.estn.economy.core.presentation.Inflation
import com.estn.economy.grossdomesticproduct.presentation.GrossDomesticProductController
import com.estn.economy.inflation.domain.FetchInflationRateUseCase
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.ResultMatcher.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [InflationController::class])
class InflationControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var fetchInflation: FetchInflationRateUseCase

    @BeforeEach
    fun setUp() {

    }

    @Test
    fun `GET inflace returns correct page`() {
        // given
        // when
        // then
        mvc.perform(get("/inflace"))
                .andExpect(
                        matchAll(
                                status().isOk,
                                view().name("pages/inflation")
                        ))

    }

    @Test
    fun `GET inflace returns correct breadcrumbs`() {
        // given
        // when
        // then
        mvc.perform(get("/inflace"))
                .andExpect(matchAll(
                        model().attribute("breadcrumbs",
                                Breadcrumbs(listOf(Home, Inflation)))
                ))

    }


}