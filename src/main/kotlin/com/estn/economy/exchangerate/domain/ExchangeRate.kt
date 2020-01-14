package com.estn.economy.exchangerate.domain

import java.util.*

/**
 * Written by estn on 13.01.2020.
 */
data class ExchangeRate(
        var date: Date,
        var currencyCode: String,
        var currencyName: String,
        var amount: Int,
        var exchangeRate: Double,
        var country: String
)