package com.estn.economy.exchangerate.database

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * Written by estn on 13.01.2020.
 */
@Repository
interface ExchangeRateRepository : JpaRepository<ExchangeRateEntity, ExchangeRateKey> {

    @Query(value = "SELECT * FROM exchange_rate WHERE date = (SELECT MAX(date) FROM exchange_rate) ORDER BY country",
            nativeQuery = true)
    fun findAllRatesFromLastDay(): Collection<ExchangeRateEntity>

}