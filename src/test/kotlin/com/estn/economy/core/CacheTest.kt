package com.estn.economy.core

import com.estn.economy.TestProfile
import com.estn.economy.core.domain.EvictAllCacheUseCase
import com.estn.economy.core.domain.OutputPercentageData
import com.estn.economy.dashboard.domain.*
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType
import com.estn.economy.grossdomesticproduct.domain.FetchGrossDomesticProductUseCase
import com.estn.economy.inflation.data.InflationRateEntity
import com.estn.economy.inflation.data.InflationType
import com.estn.economy.unemploymentrate.data.database.UnemploymentRateEntity
import com.estn.economy.utility.exampleRate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.LocalDate

@SpringBootTest
@TestProfile
class CacheTest {

    @Autowired
    lateinit var composeDashboard: ComposeDashboardUseCase

    @Autowired
    lateinit var evictCache: EvictAllCacheUseCase

    @MockBean
    lateinit var fetchGdp: FetchGrossDomesticProductUseCase

    @MockBean
    lateinit var composeOverView: ComposeEconomyOverviewUseCase

    @BeforeEach
    fun setUp() {
        evictCache.execute()

        given(fetchGdp.fetchPercentChangesPerYear(GrossDomesticProductType.REAL_2010_PRICES))
                .willReturn(listOf(OutputPercentageData(2015, 5.0), OutputPercentageData(2016, 5.7)))
        given(composeOverView.execute())
                .willReturn(EconomyOverview(
                        exchangeRate = ExchangeRatesOverview(LocalDate.now(), listOf(exampleRate)),
                        unemployment = UnemploymentRateEntity(
                                quarter = 1,
                                year = 2015,
                                unemploymentRatePercent = 5.0),
                        latestGdp = LatestGdp(2015, 5.0),
                        inflation = InflationOverview("leden",
                                InflationRateEntity(
                                        month = 1,
                                        year = 2015,
                                        type = InflationType.THIS_MONTH_VS_PREVIOUS_MONTH,
                                        valuePercent = 5.0f))))
    }

    @Test
    fun `compose dashboard usecase is cashed, fetchGdp will be executed only once`() {
        // given
        // when
        composeDashboard.execute()
        composeDashboard.execute()
        composeDashboard.execute()
        // then
        verify(fetchGdp, times(1))
                .fetchPercentChangesPerYear(GrossDomesticProductType.REAL_2010_PRICES)

        verifyNoMoreInteractions(fetchGdp)
    }


    @Test
    fun `compose dashboard usecase is cached, but its executed again after cache eviction`() {
        // given
        // when
        composeDashboard.execute()

        evictCache.execute()

        composeDashboard.execute()
        // then
        verify(fetchGdp, times(2))
                .fetchPercentChangesPerYear(GrossDomesticProductType.REAL_2010_PRICES)

        verifyNoMoreInteractions(fetchGdp)
    }

}