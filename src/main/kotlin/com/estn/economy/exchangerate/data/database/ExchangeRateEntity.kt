package com.estn.economy.exchangerate.data.database

import com.estn.economy.exchangerate.domain.ExchangeRate
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

/**
 * Written by estn on 13.01.2020.
 */
@Entity(name = "exchange_rate")
@IdClass(ExchangeRateKey::class)
data class ExchangeRateEntity(
        @Column(name = "date") @Id var date: LocalDate = LocalDate.now(),
        @Column(name = "currency_code") @Id var currencyCode: String = "",
        @Column(name = "currency_name") var currencyName: String = "",
        @Column(name = "amount") var amount: Int = 0,
        @Column(name = "exchange_rate") var exchangeRate: Double = 0.0,
        @Column(name = "country") var country: String = ""
)

val ExchangeRateEntity.key
    get() = ExchangeRateKey(this.date, this.currencyCode)


data class ExchangeRateKey(
        var date: LocalDate = LocalDate.now(),
        var currencyCode: String = "") : Serializable

fun ExchangeRate.toEntity(): ExchangeRateEntity {
    return ExchangeRateEntity(
            date = this.date,
            currencyCode = this.currencyCode,
            currencyName = this.currencyName,
            amount = this.amount,
            exchangeRate = this.exchangeRate,
            country = this.country)
}

fun ExchangeRateEntity.toDomain(): ExchangeRate {
    return ExchangeRate(
            date = this.date,
            currencyCode = this.currencyCode,
            currencyName = this.currencyName,
            amount = this.amount,
            exchangeRate = this.exchangeRate,
            country = this.country)
}