package com.estn.economy.exchangerate.data.database

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

/**
 * Written by estn on 13.01.2020.
 */
@Repository
interface ExchangeRateRepository : JpaRepository<ExchangeRateEntity, ExchangeRateKey> {

    @Query(value = "SELECT * FROM exchange_rate WHERE date = (SELECT MAX(date) FROM exchange_rate)" +
            " ORDER BY country",
            nativeQuery = true)
    fun findAllRatesFromLastDay(): Collection<ExchangeRateEntity>


    @Query(value = "SELECT * FROM exchange_rate WHERE date = (SELECT MAX(date) FROM exchange_rate)" +
            "AND currency_code IN(?1) ORDER BY country",
            nativeQuery = true)
    fun findAllRatesFromLastDayWhereCodeLike(currencyCodes: List<String>): Collection<ExchangeRateEntity>

    @Query(value = "select * from (\n" +
            "    select * from(\n" +
            "        select adddate('1970-01-01',t4.i*10000 + t3.i*1000 + t2.i*100 + t1.i*10 + t0.i) selected_date from(\n" +
            "            select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t0,(\n" +
            "            select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t1,(\n" +
            "            select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t2,(\n" +
            "            select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t3,(\n" +
            "            select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t4) v\n" +
            "        where (selected_date between ?1 and curdate()) and (DAYOFWEEK(selected_date) not in (7,1))\n" +
            "    order by selected_date) as generated_weekdays\n" +
            "where selected_date not in (select date from exchange_rate where date between ?1 and curdate());",
            nativeQuery = true)
    fun findAllWeekdaysThatAreMissing(startingDate: LocalDate): Collection<String>

    fun findFirstByOrderByDate(): ExchangeRateEntity

    fun findExchangeRateEntityByCurrencyCodeOrderByDate(currencyCode: String): List<ExchangeRateEntity>



}