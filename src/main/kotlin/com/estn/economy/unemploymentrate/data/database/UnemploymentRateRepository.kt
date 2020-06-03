package com.estn.economy.unemploymentrate.data.database

import com.estn.economy.unemploymentrate.domain.UnemploymentRatePerYearAvg
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UnemploymentRateRepository : JpaRepository<UnemploymentRateEntity, UnemploymentRateKey> {

    @Query("select new com.estn.economy.unemploymentrate.domain.UnemploymentRatePerYearAvg" +
            "(year, avg(unemploymentRatePercent)) from unemployment_rate group by year order by year asc")
    fun getAllYearlyAveragedUnemploymentRates() : List<UnemploymentRatePerYearAvg>

    fun findFirstByOrderByYearDescMonthDesc() : UnemploymentRateEntity

}