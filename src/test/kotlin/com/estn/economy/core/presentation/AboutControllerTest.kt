package com.estn.economy.core.presentation

import com.estn.economy.core.domain.FetchDataSourcesUseCase
import com.estn.economy.core.domain.Inflation
import com.estn.economy.core.domain.RealGdp
import com.estn.economy.core.presentation.controller.AboutController
import com.estn.economy.core.presentation.model.About
import com.estn.economy.core.presentation.model.Home
import com.estn.economy.core.presentation.model.Routing
import com.estn.economy.utility.breadcrumbs
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher.matchAll
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [AboutController::class])
class AboutControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var fetchDataSources: FetchDataSourcesUseCase

    val dataSources = listOf(RealGdp, Inflation)

    @BeforeEach
    fun setUp() {
        given(fetchDataSources.execute()).willReturn(dataSources)
    }

    @Test
    fun `GET oaplikaci returns correct page and breadcrumbs`() {
        // given
        // when
        // then
        mvc.perform(get(Routing.ABOUT))
                .andExpect(
                        matchAll(
                                status().isOk,
                                breadcrumbs(Home, About),
                                view().name("pages/about")
                        )
                )
    }

    @Test
    fun `GET oaplikaci zdrojedat add correct data sources to model`() {
        // given
        // when
        // then
        mvc.perform(get(Routing.ABOUT))
                .andExpect(
                        matchAll(
                                status().isOk,
                                model().attribute("dataSources", dataSources)
                        )
                )
    }


}