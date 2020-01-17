package com.estn.economy.exchangerate.database

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.util.*
import java.util.concurrent.TimeUnit

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

    @AfterEach
    fun tearDown() {
        setUp()
    }

    @Test
    fun `test saving an entity`() {
        // given
        // when
        val entityToSave = ExchangeRateEntity()
        repository.saveAndFlush(entityToSave)
        // then
        val foundRate = repository.findById(ExchangeRateKey(entityToSave.date, entityToSave.currencyCode))
        assertThat(foundRate.isPresent).isTrue()
        assertThat(foundRate.get()).isEqualTo(entityToSave)
    }

    @Test
    fun `fetch all exchange rates for latest available day`() {
        // given
        val today = Date()
        val yesteday = Date(today.time - TimeUnit.DAYS.toMillis(1))

        // prepare db data
        val todaysUSDrate = ExchangeRateEntity(date = today, currencyCode = "USD", exchangeRate = 22.5)
        val todaysCADrate = todaysUSDrate.copy(currencyCode = "CAD", exchangeRate = 24.5)
        val yesterdaysUSDrate = todaysUSDrate.copy(date = yesteday, exchangeRate = 23.5)
        val yesterdaysCADrate = todaysCADrate.copy(date = yesteday)
        // populate db with data
        with(entityManager) {
            persistAndFlush(todaysUSDrate)
            persistAndFlush(todaysCADrate)
            persistAndFlush(yesterdaysUSDrate)
            persistAndFlush(yesterdaysCADrate)
        }

        // when
        val resultRates = repository.findAllRatesFromLastDay()
        // then
        assertThat(resultRates.size).isEqualTo(2)
        assertThat(resultRates).containsOnly(todaysUSDrate, todaysCADrate)
    }

    @Test
    fun `saving same entity twice overwrites given record`() {
        // given
        val today = Date()
        val oldUSD = ExchangeRateEntity(date = today, currencyCode = "USD", exchangeRate = 22.5)
        val newUSD = oldUSD.copy(exchangeRate = 25.5)
        // when
        repository.save(oldUSD)

        repository.save(newUSD)
//        entityManager.flush()
        // then
        val findAll = repository.findAll()
        assertThat(findAll.size).isEqualTo(1)
        assertThat(findAll.first().exchangeRate).isEqualTo(25.5)
    }
}