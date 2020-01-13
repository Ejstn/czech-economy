package com.estn.economy.exchangerate

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import java.util.*

/**
 * Written by estn on 13.01.2020.
 */
@JacksonXmlRootElement(localName = "kurzy")
class ExchangeRateDto {

    @JacksonXmlProperty(isAttribute = true, localName = "banka")
    var bankName: String = ""
    @JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @JacksonXmlProperty(isAttribute = true, localName = "datum")
    var date: Date = Date()
    @JacksonXmlProperty(isAttribute = true, localName = "poradi")
    var order: Int = 0
    @JacksonXmlProperty(isAttribute = false, localName = "tabulka")
    var exchangeRatesTable: ExchangeRateTable = ExchangeRateTable()

    override fun toString(): String {
        return "ExchangeRateDto(bankName='$bankName', date=$date, order=$order, exchangeRatesTable=$exchangeRatesTable)"
    }


}

class ExchangeRateTable {
    @JacksonXmlProperty(isAttribute = true, localName = "typ")
    var type: String = ""
    @JacksonXmlElementWrapper(localName = "radek", useWrapping = true)
    var rates: Array<CurrencyExchangeRate> = arrayOf()

    override fun toString(): String {
        return "ExchangeRateTable(type='$type', rates=${rates.contentToString()})"
    }


}

class CurrencyExchangeRate {
    @JacksonXmlProperty(isAttribute = true, localName = "kod")
    var currencyCode: String = ""
    @JacksonXmlProperty(isAttribute = true, localName = "mena")
    var currencyName: String = ""
    @JacksonXmlProperty(isAttribute = true, localName = "mnozstvi")
    var amount: Int = 0
    @JacksonXmlProperty(isAttribute = true, localName = "kurz")
    var rate: Float = 0f
    @JacksonXmlProperty(isAttribute = true, localName = "zeme")
    var country: String = ""

    override fun toString(): String {
        return "CurrencyExchangeRate(currencyCode='$currencyCode', currencyName='$currencyName', amount=$amount, rate=$rate, country='$country')"
    }


}