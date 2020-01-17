package com.estn.economy.exchangerate.api

import com.estn.economy.core.DateFormatter
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

/**
 * Written by estn on 13.01.2020.
 */
@Component
class CNBClient(private val dateFormatter: DateFormatter,
                restTemplateBuilder: RestTemplateBuilder) {

    private val LOGGER = LoggerFactory.getLogger(CNBClient::class.java)

    private val BASE_URL: String =
            "https://www.cnb.cz/cs/financni_trhy/devizovy_trh/kurzy_devizoveho_trhu/denni_kurz.xml"

    private val DATE_QUERY_PARAM = "date"

    private val restTemplate: RestTemplate = restTemplateBuilder.build()

    fun fetchLatestExchangeRatesEntity(): ResponseEntity<ExchangeRateRootDto> = fetchExchangeRateForDay(Date())

    fun fetchExchangeRateForDay(date: Date): ResponseEntity<ExchangeRateRootDto> {
        val formattedDate = dateFormatter.formatDateForCnbApi(date)

        val queryParam = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam(DATE_QUERY_PARAM, formattedDate)
                .build()
                .toUri()

        val entity = restTemplate.getForEntity(queryParam, ExchangeRateRootDto::class.java)

        LOGGER.info("fetching exchange rates for date: ${formattedDate}, http: ${entity.statusCodeValue}")

        return entity
    }

}