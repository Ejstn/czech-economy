package com.estn.economy.exchangerate.data.cnbapi

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


/**
 * Written by estn on 13.01.2020.
 */
@Suppress("MemberVisibilityCanBePrivate")
class CNBApiXmlDeserializationTest {

    val input: String =
            "<kurzy banka=\"CNB\" datum=\"10.01.2020\" poradi=\"7\">\n" +
                    "<tabulka typ=\"XML_TYP_CNB_KURZY_DEVIZOVEHO_TRHU\">\n" +
                    "<radek kod=\"AUD\" mena=\"dolar\" mnozstvi=\"1\" kurz=\"15,662\" zeme=\"Austrálie\"/>\n" +
                    "</tabulka>\n" +
                    "</kurzy>"

    val xmlMapper: ObjectMapper = XmlMapper()

    @Test
    fun testDeserialization() {
        // given
        // when
        val resultExchangeRate = xmlMapper.readValue<ExchangeRateRootDto>(input)
        // then

        assertThat(resultExchangeRate.bankName).isEqualTo("CNB")

        val rates = resultExchangeRate.exchangeRatesTableDto.rates
        assertThat(rates).isNotEmpty
        assertThat(rates).hasSize(1)

        val firstRate = rates.first()
        assertThat(firstRate.rate).isEqualTo(15.662)
        assertThat(firstRate.country).isEqualTo("Austrálie")

    }

}