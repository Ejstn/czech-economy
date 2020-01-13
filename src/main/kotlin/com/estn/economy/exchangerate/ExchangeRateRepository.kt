package com.estn.economy.exchangerate

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Written by estn on 13.01.2020.
 */
@Repository
interface ExchangeRateRepository : CrudRepository<ExchangeRate, Int>