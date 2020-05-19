package com.estn.economy.nationalbudget.data

import com.estn.economy.DatabaseTest
import com.estn.economy.nationalbudget.data.PublicDebtEntity
import com.estn.economy.nationalbudget.data.PublicDebtRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@DatabaseTest
class PublicDebtRepositoryTest {

    @Autowired
    lateinit var repository: PublicDebtRepository

    @BeforeEach
    fun setUp() {
        repository.deleteAllInBatch()
    }

    @Test
    fun `test saving an entity`() {
        // given
        // when
        val entityToSave = PublicDebtEntity(year = 2015, millionsCrowns = 446545)
        repository.save(entityToSave)
        // then
        val foundRate = repository.findById(entityToSave.year)
        assertThat(foundRate.isPresent).isTrue()
        assertThat(foundRate.get()).isEqualTo(entityToSave)
    }



}