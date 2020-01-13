package com.estn.economy

import com.estn.economy.exchangerate.ExchangeRateDto
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest


/**
 * Written by estn on 13.01.2020.
 */
@SpringBootTest
class JacksonXmlDeserializationTest {

    val input: String =
            "<kurzy banka=\"CNB\" datum=\"10.01.2020\" poradi=\"7\">\n" +
                    "<tabulka typ=\"XML_TYP_CNB_KURZY_DEVIZOVEHO_TRHU\">\n" +
                    "<radek kod=\"AUD\" mena=\"dolar\" mnozstvi=\"1\" kurz=\"15,662\" zeme=\"Austrálie\"/>\n" +
                    "</tabulka>\n" +
                    "</kurzy>"

    var xmlMapper: XmlMapper = XmlMapper()

    @Test
    fun testDeserialization() {
        val result = xmlMapper.readValue<ExchangeRateDto>(input)

        assertThat(result.exchangeRatesTable.rates).isNotEmpty
    }

}