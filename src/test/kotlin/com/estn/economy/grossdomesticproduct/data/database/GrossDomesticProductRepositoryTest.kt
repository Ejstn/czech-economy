package com.estn.economy.grossdomesticproduct.data.database

import com.estn.economy.DatabaseTest
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType.REAL_2010_PRICES
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * Written by estn on 17.01.2020.
 */
@DatabaseTest
class GrossDomesticProductRepositoryTest {

    @Autowired
    lateinit var repository: GrossDomesticProductRepository

    @BeforeEach
    fun setUp() {
        repository.deleteAllInBatch()
    }

    @Test
    fun `test saving an entity`() {
        // given
        // when
        val entityToSave = GrossDomesticProductEntity(year = 2016, quarter = 4, gdpMillionsCrowns = 456465464, type = REAL_2010_PRICES)
        repository.save(entityToSave)
        // then
        val foundRate = repository.findById(entityToSave.key)
        assertThat(foundRate.isPresent).isTrue()
        assertThat(foundRate.get()).isEqualTo(entityToSave)
    }

    @Test
    fun `test getAllSummedByYearHavingAllFourQuarters`() {
        // given
        val first2016 = GrossDomesticProductEntity(
                year = 2016,
                quarter = 1,
                gdpMillionsCrowns = 1,
                type = REAL_2010_PRICES)
        val second2016 = first2016.copy(quarter = 2)
        val third2016 = first2016.copy(quarter = 3)
        val fourth2016 = first2016.copy(quarter = 4)

        val first2017 = first2016.copy(quarter = 1, year = 2017)

        repository.saveAll(listOf(first2016, second2016, third2016, fourth2016,
                first2017))
        // when
        val result = repository.getAllSummedByYearHavingAllFourQuarters(REAL_2010_PRICES)
        // then
        assertThat(result.size).isEqualTo(1)
        assertThat(result.first().year).isEqualTo(2016)
        assertThat(result.first().gdpMillions).isEqualTo(4)
    }

    @Test
    fun `test default ordering is as expected - year asc, quarter asc`() {
        // given
        val first2016 = GrossDomesticProductEntity(
                year = 2016,
                quarter = 1,
                gdpMillionsCrowns = 1,
                type = REAL_2010_PRICES)
        val second2016 = first2016.copy(quarter = 2)
        val third2016 = first2016.copy(quarter = 3)
        val fourth2016 = first2016.copy(quarter = 4)

        val first2017 = first2016.copy(quarter = 1, year = 2017)

        repository.saveAll(listOf(first2016, second2016, third2016, fourth2016,
                first2017))
        // when
        val result = repository.getAllByTypeEquals(REAL_2010_PRICES)
        // then
        assertThat(result.size).isEqualTo(5)

        assertThat(result[0]).isEqualTo(first2016)
        assertThat(result[1]).isEqualTo(second2016)
        assertThat(result[2]).isEqualTo(third2016)
        assertThat(result[3]).isEqualTo(fourth2016)
        assertThat(result[4]).isEqualTo(first2017)

    }


}