package com.estn.economy.inflation.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InflationRateRepository : JpaRepository<InflationRateEntity, InflationRateKey> {

    fun findAllByTypeAndMonthEquals(type: InflationType, month: Int): List<InflationRateEntity>

    fun findAllByTypeEquals(type: InflationType) : List<InflationRateEntity>

    fun findFirstByTypeOrderByYearDescMonthDesc(type: InflationType) : InflationRateEntity

}