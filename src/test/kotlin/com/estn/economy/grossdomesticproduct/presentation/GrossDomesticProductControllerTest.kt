package com.estn.economy.grossdomesticproduct.presentation

import com.estn.economy.core.domain.OutputPercentageData
import com.estn.economy.core.presentation.model.Gdp
import com.estn.economy.core.presentation.model.Home
import com.estn.economy.core.presentation.model.Routing
import com.estn.economy.core.presentation.utility.mapToPairs
import com.estn.economy.core.presentation.utility.quarterToRoman
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductEntity
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType.*
import com.estn.economy.grossdomesticproduct.domain.FetchGrossDomesticProductUseCase
import com.estn.economy.utility.breadcrumbs
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher.matchAll
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [GrossDomesticProductController::class])
class GrossDomesticProductControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var fetchGdp: FetchGrossDomesticProductUseCase

    val realGdp = listOf(GrossDomesticProductEntity(
            year = 2020,
            type = REAL_2010_PRICES,
            gdpMillionsCrowns = 464654,
            quarter = 3
    ))

    val realChanges = listOf(
            OutputPercentageData(2015, 4.0, GrossDomesticProductEntity(
                    year = 2015, quarter = 4, gdpMillionsCrowns = 54564, type = REAL_2010_PRICES))
    )

    @BeforeEach
    fun setUp() {

        given(fetchGdp.fetchGdp(REAL_2010_PRICES))
                .willReturn(realGdp)


        given(fetchGdp.fetchPercentChangesPerQuarter(REAL_2010_PRICES))
                .willReturn(realChanges)

    }

    @Test
    fun `GET hdp returns correct page`() {
        // given
        // when
        // then
        mvc.perform(get(Routing.GDP))
                .andExpect(
                        matchAll(
                                status().isOk,
                                view().name("pages/gdp")
                        ))

    }

    @Test
    fun `GET hdp returns correct model`() {
        // given
        // when
        // then
        mvc.perform(get(Routing.GDP))
                .andExpect(
                        matchAll(
                                model().attribute("realGdp2010Prices", realGdp
                                        .mapToPairs { Pair("${it.quarter.quarterToRoman()} ${it.year}", it.gdpMillionsCrowns) }),
                                model().attribute("realGdpChanges", realChanges.mapToPairs {
                                    Pair("${it.dataPoint.quarter.quarterToRoman()} ${it.dataPoint.year}", it.value)
                                })
                        ))

    }

    @Test
    fun `GET hdp returns correct breadcrumbs`() {
        // given
        // when
        // then
        mvc.perform(get(Routing.GDP))
                .andExpect(
                        matchAll(
                                breadcrumbs(Home, Gdp)
                        ))

    }


}