package com.estn.economy

import com.estn.economy.core.DateFormatter
import com.estn.economy.exchangerate.ExchangeRateService
import com.estn.economy.utility.exampleRate
import com.estn.economy.utility.mockLatestRates
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Written by estn on 16.01.2020.
 */
@Suppress("MemberVisibilityCanBePrivate")
@WebMvcTest(controllers = [EconomyController::class])
class EconomyControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var service: ExchangeRateService

    @MockBean
    lateinit var dateFormatter: DateFormatter

    val date = "15.1.2020"

    @Test
    fun `GET root route returns dashboard`() {
        // given
        val expectedRates = listOf(exampleRate)
        val expectedDashboard = EconomyDashboard(date, expectedRates)

        service.mockLatestRates(expectedRates)
        `when`(dateFormatter.formatDateForFrontEnd(exampleRate.date)).thenReturn(date)
        // when
        // then
        mvc.perform(get("/"))
                .andExpect {
                    status().is2xxSuccessful
                    model().attribute("dashboard", expectedDashboard)
                }
    }
}