package com.estn.economy.exchangerate.database

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * Written by estn on 13.01.2020.
 */
@Entity(name = "echange_rate")
data class ExchangeRateEntity(
        @Id @GeneratedValue
        var id: Int = 0,
        @Column(name = "date") var date: Date = Date(),
        @Column(name = "currency_code") var currencyCode: String = "",
        @Column(name = "currency_name") var currencyName: String = "",
        @Column(name = "amount") var amount: Int = 0,
        @Column(name = "exchange_rate") var exchangeRate: Float = 0f,
        @Column(name = "country") var country: String = ""
)