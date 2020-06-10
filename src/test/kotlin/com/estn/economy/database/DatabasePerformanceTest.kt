package com.estn.economy.database

import com.estn.economy.DatabaseTest
import com.estn.economy.exchangerate.data.database.ExchangeRateEntity
import com.estn.economy.exchangerate.data.database.ExchangeRateRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

@DatabaseTest
@Disabled
class DatabasePerformanceTest {

    @Autowired
    lateinit var repository: ExchangeRateRepository

    @BeforeEach
    fun setUp() {
        repository.deleteAllInBatch()
    }

    @Test
    fun `test insert speed of 10 000 items`() {
        // given
        val numberOfItems = 10_000
        val items = generateItems(numberOfItems)
        // when
        val insertStart = System.currentTimeMillis()
        repository.saveAll(items)
        // then
        println("inserted $numberOfItems items in: ${System.currentTimeMillis() - insertStart} millis")
    }

    @Test
    fun `test insert speed of 100 000 items`() {
        // given
        val numberOfItems = 100_000
        val items = generateItems(numberOfItems)
        // when
        val insertStart = System.currentTimeMillis()
        repository.saveAll(items)
        // then
        println("inserted $numberOfItems items in: ${System.currentTimeMillis() - insertStart} millis")
    }

    @Test
    fun `test insert speed of 1 000 000 items`() {
        // given
        val numberOfItems = 1_000_000
        val items = generateItems(numberOfItems)
        // when
        val insertStart = System.currentTimeMillis()
        repository.saveAll(items)
        // then
        println("inserted $numberOfItems items in: ${System.currentTimeMillis() - insertStart} millis")
    }

    @Test
    fun `test delete speed of 1_000_000 items in batch`() {
        // given
        val numberOfItems = 1_000_000
        val items = generateItems(numberOfItems)
        repository.saveAll(items)
        // when
        val deleteStart = System.currentTimeMillis()
        repository.deleteAllInBatch()
        // then
        println("deleted $numberOfItems items in batch in: ${System.currentTimeMillis() - deleteStart} millis")
    }


    private fun generateItems(itemsCount: Int): List<ExchangeRateEntity> {
        val startingDate = LocalDate.of(1900, 1, 1)
        val entity = ExchangeRateEntity(
                startingDate,
                "USD",
                "dolar",
                1,
                15.57,
                "USA")

        return (0 until itemsCount)
                .map {
                    entity.copy(
                            date = entity.date.plusDays(it.toLong()),
                            exchangeRate = Math.random() * 10)
                }
                .toList()

    }
}