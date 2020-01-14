package com.estn.economy.exchangerate

import com.estn.economy.exchangerate.api.CNBClient
import com.estn.economy.exchangerate.api.toDomain
import com.estn.economy.exchangerate.database.ExchangeRateRepository
import com.estn.economy.exchangerate.domain.ExchangeRate
import org.springframework.stereotype.Service

/**
 * Written by estn on 13.01.2020.
 */
@Service
class ExchangeRateService(private val cnbClient: CNBClient,
                          private val exchangeRepository: ExchangeRateRepository) {

    fun fetchExchangeRates(): Collection<ExchangeRate> {
        return cnbClient.fetchExchangeRate().toDomain()
    }

}