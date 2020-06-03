package com.estn.economy.core.data.api

import com.estn.economy.core.data.api.converter.CsvHttpMessageConverter
import com.estn.economy.core.domain.date.DateFormatter
import com.estn.economy.core.domain.date.format
import com.estn.economy.core.domain.getLogger
import com.estn.economy.exchangerate.data.api.ExchangeRateRootDto
import com.estn.economy.salary.data.api.SalaryDto
import com.estn.economy.salary.data.api.SallaryRootDto
import com.estn.economy.unemploymentrate.data.api.UnemploymentRateDto
import com.estn.economy.unemploymentrate.data.api.UnemploymentRateRootDto
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.time.LocalDate

/**
 * Written by estn on 13.01.2020.
 */
@Component
class CNBClient(private val dateFormatter: DateFormatter,
                restTemplateBuilder: RestTemplateBuilder) {

    private val LOGGER = getLogger(this::class.java)


    val DAILY_EXCHANGE_RATES_URL: String =
            "https://www.cnb.cz/cs/financni_trhy/devizovy_trh/kurzy_devizoveho_trhu/denni_kurz.xml"

    val DATE_QUERY = "date"

    val NOMINAL_AVERAGE_QUARTERLY_SALARIES_URL: String = "https://www.cnb.cz/cnb/STAT.ARADY_PKG.VYSTUP?p_period=3&p_sort=2&p_des=50&p_sestuid=21737&p_uka=1&p_strid=ACFA&p_lang=CS&p_format=2&p_decsep=."

    val MONTHLY_UNEMPLOYMENT_URL: String = "https://www.cnb.cz/cnb/STAT.ARADY_PKG.VYSTUP?p_period=1&p_sort=2&p_des=50&p_sestuid=21751&p_uka=1&p_strid=ACHAB&p_lang=CS&p_format=2&p_decsep=."

    val FROM_QUERY = "p_od"
    val TO_QUERY = "p_do"

    val ARAD_API_FROM_TO_DATE_QUERY_PATTERN = "yyyyMM"


    private val restTemplate: RestTemplate = restTemplateBuilder
            .additionalMessageConverters(
                    object : CsvHttpMessageConverter<SalaryDto, SallaryRootDto>() {},
                    object : CsvHttpMessageConverter<UnemploymentRateDto, UnemploymentRateRootDto>() {})
            .build()

    fun fetchExchangeRateForDay(date: LocalDate = LocalDate.now()): ResponseEntity<ExchangeRateRootDto> {
        val formattedDate = dateFormatter.formatDateForCnbApi(date)

        val url = UriComponentsBuilder.fromHttpUrl(DAILY_EXCHANGE_RATES_URL)
                .queryParam(DATE_QUERY, formattedDate)
                .build()
                .toUri()

        val entity = restTemplate.getForEntity(url, ExchangeRateRootDto::class.java)

        LOGGER.info("fetching exchange rates for date: ${formattedDate}, http: ${entity.statusCodeValue}")

        return entity
    }

    fun fetchNominalAverageSalaries(from: LocalDate, to: LocalDate): List<SalaryDto> {

        val url = UriComponentsBuilder.fromHttpUrl(NOMINAL_AVERAGE_QUARTERLY_SALARIES_URL)
                .queryParam(FROM_QUERY, from.formatForArad())
                .queryParam(TO_QUERY, to.formatForArad())
                .build()
                .toUri()

        val result = restTemplate.getForEntity(url, SallaryRootDto::class.java)

        LOGGER.info("fetching nominal average salaries within range $from - $to, http: ${result.statusCodeValue}")

        return result.body
                ?.list ?: throw IllegalStateException("")
    }

    fun fetchMonthlyUnemploymentRates(from: LocalDate, to: LocalDate): List<UnemploymentRateDto> {

        val url = UriComponentsBuilder.fromHttpUrl(MONTHLY_UNEMPLOYMENT_URL)
                .queryParam(FROM_QUERY, from.formatForArad())
                .queryParam(TO_QUERY, to.formatForArad())
                .build()
                .toUri()

        val result = restTemplate.getForEntity(url, UnemploymentRateRootDto::class.java)

        LOGGER.info("fetching monthly unemployment rates $from - $to, http: ${result.statusCodeValue}")

        return result.body
                ?.list ?: throw IllegalStateException("")

    }

    fun LocalDate.formatForArad(): String = this.format(ARAD_API_FROM_TO_DATE_QUERY_PATTERN)

}