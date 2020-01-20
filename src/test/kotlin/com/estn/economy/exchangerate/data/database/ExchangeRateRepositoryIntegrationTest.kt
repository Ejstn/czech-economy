package com.estn.economy.exchangerate.data.database

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

/**
 * Written by estn on 17.01.2020.
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExchangeRateRepositoryIntegrationTest {

    @Autowired
    lateinit var repository: ExchangeRateRepository

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
}