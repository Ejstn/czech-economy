package com.estn.economy.exchangerate.database

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles

/**
 * Written by estn on 17.01.2020.
 */
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExchangeRateRepositoryTest {

    @Autowired
    lateinit var repository: ExchangeRateRepository

    @Autowired
    lateinit var entityManager: TestEntityManager

    @BeforeEach
    fun setUp() {
        repository.deleteAllInBatch()
        entityManager.flush()
    }

    @Test
    fun `test saving an entity`() {
        // given
        repository.deleteAllInBatch()
        entityManager.flush()
        // when
        val entityToSave = ExchangeRateEntity()
        repository.saveAndFlush(entityToSave)
        // then
        val foundRate = repository.findById(ExchangeRateKey(entityToSave.date, entityToSave.currencyCode))
        assertThat(foundRate.isPresent).isTrue()
        assertThat(foundRate.get()).isEqualTo(entityToSave)
    }
}