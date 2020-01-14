package com.estn.economy.exchangerate.api

import com.estn.economy.exchangerate.domain.ExchangeRate
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import java.util.*

/**
 * Written by estn on 13.01.2020.
 */
@JacksonXmlRootElement(localName = "kurzy")
class ExchangeRateRootDto {

    @JacksonXmlProperty(isAttribute = true, localName = "banka")
    lateinit var bankName: String
    @JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @JacksonXmlProperty(isAttribute = true, localName = "datum")
    lateinit var date: Date
    @JacksonXmlProperty(isAttribute = true, localName = "poradi")
    var order: Int = 0
    @JacksonXmlProperty(localName = "tabulka")
    lateinit var exchangeRatesTableDto: ExchangeRateTableDto

    override fun toString(): String {
        return "ExchangeRateDto(bankName='$bankName', date=$date, order=$order, exchangeRatesTable=$exchangeRatesTableDto)"
    }

}

fun ExchangeRateRootDto.toDomain(): Collection<ExchangeRate> {
    val date = this.date
    return this.exchangeRatesTableDto.rates.map {
        it.toDomain(date)
    }
}

class ExchangeRateTableDto {
    @JacksonXmlProperty(isAttribute = true, localName = "typ")
    lateinit var type: String

    @JacksonXmlProperty(localName = "radek")
    @JacksonXmlElementWrapper(useWrapping = false)
    lateinit var rates: Collection<ExchangeRateDto>

    override fun toString(): String {
        return "ExchangeRateTable(type='$type', rates=${rates})"
    }

}

class ExchangeRateDto {
    @JacksonXmlProperty(isAttribute = true, localName = "kod")
    lateinit var currencyCode: String
    @JacksonXmlProperty(isAttribute = true, localName = "mena")
    lateinit var currencyName: String
    @JacksonXmlProperty(isAttribute = true, localName = "mnozstvi")
    var amount: Int = 0
    @JsonDeserialize(using = WeirdCNBStringToDoubleDeserializer::class)
    @JacksonXmlProperty(isAttribute = true, localName = "kurz")
    var rate: Double = 0.0
    @JacksonXmlProperty(isAttribute = true, localName = "zeme")
    lateinit var country: String

    override fun toString(): String {
        return "CurrencyExchangeRate(currencyCode='$currencyCode', currencyName='$currencyName', amount=$amount, rate=$rate, country='$country')"
    }

}

fun ExchangeRateDto.toDomain(date: Date): ExchangeRate {
    return ExchangeRate(date = date, currencyCode = this.currencyCode, currencyName = this.currencyName
            , amount = this.amount, country = this.country, exchangeRate = this.rate)
}