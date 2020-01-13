package com.estn.economy.exchangerate

import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

/**
 * Written by estn on 13.01.2020.
 */
@Component
class CNBClient(restTemplateBuilder: RestTemplateBuilder) {

    private val LOGGER = LoggerFactory.getLogger(CNBClient::class.java)

    private val BASE_URL: String =
            "https://www.cnb.cz/cs/financni_trhy/devizovy_trh/kurzy_devizoveho_trhu/denni_kurz.xml"

    private val restTemplate: RestTemplate = restTemplateBuilder.build()

    fun fetchExchangeRate() : ExchangeRateDto {
        val entity = restTemplate.getForEntity(BASE_URL, ExchangeRateDto::class.java)

        LOGGER.info(entity.toString())

        return entity.body!!
    }

}