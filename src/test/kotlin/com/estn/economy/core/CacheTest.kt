package com.estn.economy.core

import com.estn.economy.TestProfile
import com.estn.economy.core.domain.EvictAllCacheUseCase
import com.estn.economy.core.domain.OutputPercentageData
import com.estn.economy.dashboard.domain.ComposeDashboardUseCase
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType
import com.estn.economy.grossdomesticproduct.domain.FetchGrossDomesticProductUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
@TestProfile
class CacheTest {

    @Autowired
    lateinit var composeDashboard: ComposeDashboardUseCase

    @Autowired
    lateinit var evictCache: EvictAllCacheUseCase

    @MockBean
    lateinit var fetchGdp: FetchGrossDomesticProductUseCase

    @BeforeEach
    fun setUp() {
        evictCache.execute()

        given(fetchGdp.fetchPercentChangesPerYear(GrossDomesticProductType.REAL_2010_PRICES))
                .willReturn(listOf(OutputPercentageData(2015, 5.0), OutputPercentageData(2016, 5.7)))
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