package com.estn.economy.exchangerate.data.api

import com.estn.economy.core.data.api.CNBClient
import com.estn.economy.core.domain.date.DateFormatter
import com.estn.economy.utility.any
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import java.time.LocalDate

/**
 * Written by estn on 17.01.2020.
 */
@RestClientTest(CNBClient::class)
class CNBClientTest {

    val EXCHANGE_BASE_URL = "https://www.cnb.cz/cs/financni_trhy/devizovy_trh/kurzy_devizoveho_trhu/denni_kurz.xml"

    @Autowired
    lateinit var mockRestServer: MockRestServiceServer

    @Autowired
    lateinit var client: CNBClient

    @MockBean
    lateinit var dateFormatter: DateFormatter

    @Test
    fun `client fetches and parses exchange rates for given day`() {
        // given
        val mockFormattedDate = "17.01.2020"
        given(dateFormatter.formatDateForCnbApi(any(LocalDate::class.java))).willReturn(mockFormattedDate)

        mockRestServer
                .expect(requestTo("${EXCHANGE_BASE_URL}?date=${mockFormattedDate}"))
                .andRespond(
                        withSuccess(ClassPathResource("test_exchange_rates.xml", javaClass), MediaType.APPLICATION_XML)
                )

        // when
        val resultEntity = client.fetchExchangeRateForDay(LocalDate.now())
        val rootDto: ExchangeRateRootDto = resultEntity.body!!
        // then
        assertThat(rootDto.bankName).isEqualTo("CNB")
        assertThat(rootDto.order).isEqualTo(7)
        assertThat(rootDto.date).isEqualTo(LocalDate.of(2020, 1, 17))
        assertThat(rootDto.exchangeRatesTableDto.type).isEqualTo("XML_TYP_CNB_KURZY_DEVIZOVEHO_TRHU")

        val rates = rootDto.exchangeRatesTableDto.rates
        assertThat(rates.size).isEqualTo(2)

        val firstRate = rates.first()
        assertThat(firstRate.currencyCode).isEqualTo("EUR")
        assertThat(firstRate.currencyName).isEqualTo("euro")
        assertThat(firstRate.amount).isEqualTo(1)
        assertThat(firstRate.rate).isEqualTo(25.265)
        assertThat(firstRate.country).isEqualTo("EMU")
    }

    @Test
    fun `client fetches and parses average salaries for given date range`() {
        // given
        val from = LocalDate.of(2019, 1, 1)
        val to = from.plusYears(1)

        val url = "https://www.cnb.cz/cnb/STAT.ARADY_PKG.VYSTUP" +
                "?p_period=3&p_sort=2&p_des=50&p_sestuid=21737&p_uka=1" +
                "&p_strid=ACFA&p_lang=CS&p_format=2&p_decsep=.&p_od=201901&p_do=202001"

        mockRestServer
                .expect(requestTo(url))
                .andRespond(
                        withSuccess(ClassPathResource("test_average_salaries.txt", javaClass), MediaType.TEXT_PLAIN)
                )

        // when
        val result = client.fetchNominalAverageSalaries(from = from, to = to)

        // then
        assertThat(result.size).isEqualTo(4)

        assertThat(result.first().date).isEqualTo(LocalDate.of(2019,12,31))
        assertThat(result.first().salaryCrowns).isEqualTo(36144)

        assertThat(result.last().date).isEqualTo(LocalDate.of(2019,3,31))
        assertThat(result.last().salaryCrowns).isEqualTo(32489)
    }

}