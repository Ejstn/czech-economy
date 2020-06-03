package com.estn.economy.inflation.presentation

import com.estn.economy.core.presentation.model.Home
import com.estn.economy.core.presentation.model.Inflation
import com.estn.economy.core.presentation.model.Routing
import com.estn.economy.inflation.data.InflationRateEntity
import com.estn.economy.inflation.data.InflationType
import com.estn.economy.inflation.domain.FetchInflationRateUseCase
import com.estn.economy.utility.breadcrumbs
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [InflationController::class])
class InflationControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var fetchInflation: FetchInflationRateUseCase

    val inflation = InflationRateEntity(
            month = 12,
            year = 2015,
            type = InflationType.THIS_YEAR_VS_LAST_YEAR,
            valuePercent = 5.5f)

    @BeforeEach
    fun setUp() {
        given(fetchInflation.fetchAllYearlyInflationRates()).willReturn(listOf(inflation))

        given(fetchInflation.fetchAllInflationRatesByType(InflationType.THIS_MONTH_VS_PREVIOUS_MONTH))
                .willReturn(listOf(inflation.copy(type = InflationType.THIS_MONTH_VS_PREVIOUS_MONTH)))

    }

    @Test
    fun `GET inflace returns correct page`() {
        // given
        // when
        // then
        mvc.perform(get(Routing.INFLATION))
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
        mvc.perform(get(Routing.INFLATION))
                .andExpect(matchAll(
                        breadcrumbs(Home, Inflation)
                ))

    }


}