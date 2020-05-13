package com.estn.economy.grossdomesticproduct.data.database

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

/**
 * Written by estn on 17.01.2020.
 */
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GrossDomesticProductRepositoryTest {

    @Autowired
    lateinit var repository: GrossDomesticProductRepository

    @Test
    fun `test saving an entity`() {
        // given
        // when
        val entityToSave = GrossDomesticProductEntity(year = 2016, gdpMillionsCrowns = 456465464, type = GrossDomesticProductType.REAL_2010_PRICES)
        repository.save(entityToSave)
        // then
        val foundRate = repository.findById(entityToSave.key())
        assertThat(foundRate.isPresent).isTrue()
        assertThat(foundRate.get()).isEqualTo(entityToSave)
    }

}