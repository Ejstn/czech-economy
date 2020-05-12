package com.estn.economy.dashboard.presentation

import com.estn.economy.dashboard.domain.ComposeDashboardUseCase
import com.estn.economy.grossdomesticproduct.domain.GrossDomesticProductPerYear
import com.estn.economy.inflation.data.InflationRateEntity
import com.estn.economy.inflation.data.InflationType
import com.estn.economy.publicdebt.data.PublicDebtEntity
import com.estn.economy.unemploymentrate.domain.UnemploymentRatePerYearAvg
import com.estn.economy.utility.exampleRate
import com.estn.economy.utility.mockDashboard
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/**
 * Written by estn on 16.01.2020.
 */
@Suppress("MemberVisibilityCanBePrivate")
@WebMvcTest(controllers = [DashboardController::class])
class DashboardControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var composeDashboard: ComposeDashboardUseCase

    val date = "15.1.2020"

    val exampleGDP = listOf(GrossDomesticProductPerYear(year = 2015, millionsCrowns = 5644787))
    val exampleUnemployment = listOf(UnemploymentRatePerYearAvg(2015, unemploymentRatePercent = 5.7))

    val expectedRates = listOf(exampleRate)
    val expectedInflation = listOf(InflationRateEntity(month = 12, year = 2015, type = InflationType.THIS_YEAR_VS_LAST_YEAR,
    valuePercent = 5f))

    val expectedPublicDebt = listOf(PublicDebtEntity(year = 2015, millionsCrowns = 1564654))

    val expectedDashboard = ComposeDashboardUseCase.EconomyDashboard(date,
            expectedRates, exampleGDP, exampleUnemployment, expectedInflation, expectedPublicDebt)

    @Test
    fun `GET root route returns dashboard`() {
        // given
        composeDashboard.mockDashboard(expectedDashboard)

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
        given(composeDashboard.execute()).willThrow(RuntimeException("whoops"))
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




}