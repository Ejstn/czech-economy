package com.estn.economy.core

import com.estn.economy.TestProfile
import com.estn.economy.core.domain.EvictAllCacheUseCase
import com.estn.economy.core.domain.OutputPercentageData
import com.estn.economy.dashboard.domain.*
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType
import com.estn.economy.grossdomesticproduct.domain.FetchGrossDomesticProductUseCase
import com.estn.economy.inflation.data.InflationRateEntity
import com.estn.economy.inflation.data.InflationType
import com.estn.economy.salary.data.database.SalaryEntity
import com.estn.economy.salary.data.database.SalaryRepository
import com.estn.economy.salary.domain.FetchSalaryUseCase
import com.estn.economy.unemploymentrate.data.database.UnemploymentRateEntity
import com.estn.economy.utility.exampleRate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.LocalDate

@SpringBootTest
@TestProfile
class CacheTest {

    @Autowired
    lateinit var fetchSalary: FetchSalaryUseCase

    @Autowired
    lateinit var evictCache: EvictAllCacheUseCase

    @MockBean
    lateinit var salaryRepository: SalaryRepository

    @BeforeEach
    fun setUp() {
        evictCache.execute()
    }

    @Test
    fun `fetch all is cached if result isnt empty`() {
        // given
        given(salaryRepository.findAll()).willReturn(
                listOf(SalaryEntity()))
        // when
        fetchSalary.fetchAll()
        fetchSalary.fetchAll()
        fetchSalary.fetchAll()
        // then
        verify(salaryRepository, times(1)).findAll()
        verifyNoMoreInteractions(salaryRepository)
    }


    @Test
    fun `fetch all is NOT cached when result is empty`() {
        // given
        given(salaryRepository.findAll()).willReturn(
                listOf())
        // when
        fetchSalary.fetchAll()
        fetchSalary.fetchAll()
        fetchSalary.fetchAll()
        // then
        verify(salaryRepository, times(3)).findAll()
    }

    @Test
    fun `fetch all is cached then evicted and then cached again`() {
        // given
        given(salaryRepository.findAll()).willReturn(
                listOf(SalaryEntity()))
        // when
        fetchSalary.fetchAll()

        evictCache.execute()

        fetchSalary.fetchAll()
        fetchSalary.fetchAll()
        // then
        verify(salaryRepository, times(2)).findAll()
    }


}