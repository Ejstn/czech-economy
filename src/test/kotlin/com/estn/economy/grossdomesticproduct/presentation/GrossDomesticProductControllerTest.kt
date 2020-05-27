package com.estn.economy.grossdomesticproduct.presentation

import com.estn.economy.core.domain.OutputPercentageData
import com.estn.economy.core.presentation.Breadcrumbs
import com.estn.economy.core.presentation.Gdp
import com.estn.economy.core.presentation.Home
import com.estn.economy.core.presentation.Routing
import com.estn.economy.core.presentation.utility.mapToPairs
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductEntity
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType
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

    val nominalGdp = listOf(GrossDomesticProductEntity(
            year = 2015,
            type = GrossDomesticProductType.NOMINAL,
            gdpMillionsCrowns = 1515151))

    val realGdp = listOf(GrossDomesticProductEntity(
            year = 2020,
            type = GrossDomesticProductType.REAL_2010_PRICES,
            gdpMillionsCrowns = 464654
    ))

    val nominalChanges = listOf(
            OutputPercentageData(2015, 5.0)
    )

    val realChanges = listOf(
            OutputPercentageData(2015, 4.0)
    )



    @BeforeEach
    fun setUp() {
        given(fetchGdp.fetchGdp(GrossDomesticProductType.NOMINAL))
                .willReturn(nominalGdp)

        given(fetchGdp.fetchGdp(GrossDomesticProductType.REAL_2010_PRICES))
                .willReturn(realGdp)

        given(fetchGdp.fetchPercentChangesPerYear(GrossDomesticProductType.NOMINAL))
                .willReturn(nominalChanges)

        given(fetchGdp.fetchPercentChangesPerYear(GrossDomesticProductType.REAL_2010_PRICES))
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
                                model().attribute("nominalGdp", nominalGdp.mapToPairs()),
                                model().attribute("realGdp2010Prices", realGdp.mapToPairs()),
                                model().attribute("nominalGdpChanges", nominalChanges.mapToPairs()),
                                model().attribute("realGdpChanges", realChanges.mapToPairs())
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