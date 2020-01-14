package com.estn.economy.exchangerate.database

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Written by estn on 13.01.2020.
 */
@Repository
interface ExchangeRateRepository : CrudRepository<ExchangeRateEntity, Int>