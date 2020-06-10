package com.estn.economy.dashboard.presentation

import com.estn.economy.core.domain.OutputPercentageData
import com.estn.economy.core.presentation.formatting.*
import com.estn.economy.core.presentation.utility.mapToPairs
import com.estn.economy.dashboard.domain.*
import com.estn.economy.inflation.data.InflationRateEntity
import com.estn.economy.inflation.data.InflationType
import com.estn.economy.nationalbudget.data.PublicDebtEntity
import com.estn.economy.salary.data.database.SalaryEntity
import com.estn.economy.unemploymentrate.data.database.UnemploymentRateEntity
import com.estn.economy.unemploymentrate.domain.model.UnemploymentRatePerYearAvg
import com.estn.economy.utility.exampleRate
import com.estn.economy.utility.mockDashboard
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate

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

    val gdp = listOf(OutputPercentageData(order = 2015, value = 5.5, dataPoint = 0))
    val unemp = listOf(UnemploymentRatePerYearAvg(2015, unemploymentRatePercent = 5.7))

    val inflation = listOf(InflationRateEntity(month = 12, year = 2015, type = InflationType.THIS_YEAR_VS_LAST_YEAR,
            valuePercent = 5f))

    val publicDebt = listOf(PublicDebtEntity(year = 2015, millionsCrowns = 1564654))

    val overview = EconomyOverview(
            exchangeRate = ExchangeRatesOverview(LocalDate.now(), listOf(exampleRate)),
          firstRow = listOf())

    val expectedDashboard = ComposeDashboardUseCase.EconomyDashboard(
            overview = overview,
            realGdp2010PricesPercentChange = gdp.mapToPairs(),
            publicDebt = publicDebt.mapToPairs(),
            yearlyInflationRates = inflation.mapToPairs(),
            yearlyUnempRates = unemp.mapToPairs()
    )


    @Test
    fun `GET root route returns dashboard`() {
        // given
        composeDashboard.mockDashboard(expectedDashboard)

        // when
        // then
        mvc.perform(get("/"))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk,
                        model().attribute("dashboard", expectedDashboard),
                        view().name("pages/dashboard")
                ))
    }

    @Test
    fun `GET that results in internal server error is handled`() {
        // given
        given(composeDashboard.execute()).willThrow(RuntimeException("whoops"))
        // when
        // then
        mvc.perform(get("/"))
                .andExpect(ResultMatcher.matchAll(
                        status().isInternalServerError,
                        model().attributeDoesNotExist("dashboard"),
                        model().attribute("status", 500),
                        view().name("error/5xx")
                ))
    }


}