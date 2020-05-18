package com.estn.economy.exchangerate.data.database

import com.estn.economy.DatabaseTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

/**
 * Written by estn on 17.01.2020.
 */
@DatabaseTest
class ExchangeRateRepositoryIntegrationTest {

    @Autowired
    lateinit var repository: ExchangeRateRepository

    @BeforeEach
    fun setUp() {
        repository.deleteAllInBatch()
    }

    @Test
    fun `saving same entity twice overwrites given record`() {
        // given
        val today = LocalDate.now()
        val oldUSD = ExchangeRateEntity(date = today, currencyCode = "USD", exchangeRate = 22.5)
        val newUSD = oldUSD.copy(exchangeRate = 25.5)
        // when
        // then

        repository.save(oldUSD)
        assertThat(repository.findById(oldUSD.key()).get().exchangeRate).isEqualTo(oldUSD.exchangeRate)

        repository.save(newUSD)
        assertThat(repository.findById(newUSD.key()).get().exchangeRate).isEqualTo(newUSD.exchangeRate)
    }

    @Test
    fun `entity is saved with correct date`() {
        // given
        val today = LocalDate.of(2020, 1, 18)
        val usdEntity = ExchangeRateEntity(date = today, currencyCode = "USD", exchangeRate = 22.5)
        // when
        val saved = repository.save(usdEntity)
        assertThat(saved).isEqualTo(usdEntity)
        // then
        assertThat(repository.findById(usdEntity.key()).get()).isEqualTo(usdEntity)

    }

    @Test
    fun `test retrieve all exchange rates from last day and with given code`() {
        // given
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)

        val usdToday = ExchangeRateEntity(date = today, currencyCode = "USD", exchangeRate = 22.5)
        val usdYesterday = ExchangeRateEntity(date = yesterday, currencyCode = "USD", exchangeRate = 20.5)

        val eurToday = ExchangeRateEntity(date = today, currencyCode = "EUR", exchangeRate = 22.5)
        val eurYesterday = ExchangeRateEntity(date = yesterday, currencyCode = "EUR", exchangeRate = 28.5)

        repository.saveAll(
                listOf(usdToday, usdYesterday,
                        eurToday, eurYesterday))

        // when
        val result = repository.findAllRatesFromLastDayWhereCodeLike(listOf("USD"))
        // then
        assertThat(result.size).isEqualTo(1)
        assertThat(result.first()).isEqualTo(usdToday)
    }
}