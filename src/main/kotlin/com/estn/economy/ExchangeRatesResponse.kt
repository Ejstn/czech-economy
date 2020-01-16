package com.estn.economy

import com.estn.economy.exchangerate.domain.ExchangeRate

/**
 * Written by estn on 16.01.2020.
 */
data class ExchangeRatesResponse (val rates: Collection<ExchangeRate>)

data class EconomyDashboard(val exchangeRatesDate: String,
                            val exchangeRates: Collection<ExchangeRate>)