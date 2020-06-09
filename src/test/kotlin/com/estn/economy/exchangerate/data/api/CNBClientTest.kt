package com.estn.economy.exchangerate.data.api

import com.estn.economy.core.data.api.CNBClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
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

    @Test
    fun `client fetches and parses exchange rates for given day`() {
        // given
        mockRestServer
                .expect(requestTo("${EXCHANGE_BASE_URL}?date=17.01.2020"))
                .andRespond(
                        withSuccess(ClassPathResource("test_exchange_rates.xml", javaClass), MediaType.APPLICATION_XML)
                )

        // when
        val resultEntity = client.fetchExchangeRateForDay(LocalDate.of(2020, 1, 17))
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

        val first = result.first()
        assertThat(first.date).isEqualTo(LocalDate.of(2019, 12, 31))
        assertThat(first.salaryCrowns).isEqualTo(36144)
        assertThat(first.isValid).isTrue()

        val last = result.last()
        assertThat(last.date).isEqualTo(LocalDate.of(2019, 3, 31))
        assertThat(last.salaryCrowns).isEqualTo(32489)
        assertThat(last.isValid).isTrue()
    }

    @Test
    fun `client fetches and parses average unemployment rates for given date range`() {
        // given
        val from = LocalDate.of(2019, 1, 1)
        val to = from.plusYears(1)

        val url = "https://www.cnb.cz/cnb/STAT.ARADY_PKG.VYSTUP?p_period=1&" +
                "p_sort=2&p_des=50&p_sestuid=21751&p_uka=1&p_strid=ACHAB&p_lang=CS&p_format=2&p_decsep=." +
                "&p_od=201901&p_do=202001"

        mockRestServer
                .expect(requestTo(url))
                .andRespond(
                        withSuccess(ClassPathResource("test_monthly_unemployment.txt", javaClass),
                                MediaType.TEXT_PLAIN)
                )

        // when
        val result = client.fetchMonthlyUnemploymentRates(from = from, to = to)

        // then
        assertThat(result.size).isEqualTo(4)

        val first = result.first()
        assertThat(first.date).isEqualTo(LocalDate.of(2020, 4, 30))
        assertThat(first.unemploymentRate).isEqualTo(2.3)
        assertThat(first.isValid).isTrue()

        val last = result.last()
        assertThat(last.date).isEqualTo(LocalDate.of(2020, 1, 31))
        assertThat(last.unemploymentRate).isEqualTo(2.1)
        assertThat(last.isValid).isTrue()
    }

    @Test
    fun `client fetches and parses quarterly real gdp for given date range`() {
        // given
        val from = LocalDate.of(2019, 1, 1)
        val to = from.plusYears(1)

        val url = "https://www.cnb.cz/cnb/STAT.ARADY_PKG.VYSTUP?p_period=3&p_sort=2&p_des=3&p_sestuid=29930&p_uka=1" +
                "&p_strid=ACL&p_lang=CS&p_format=2&p_decsep=." +
                "&p_od=201901&p_do=202001"

        mockRestServer
                .expect(requestTo(url))
                .andRespond(
                        withSuccess(ClassPathResource("test_real_quarterly_gdp_2010_prices.txt", javaClass),
                                MediaType.TEXT_PLAIN)
                )

        // when
        val result = client.fetchQuarterlyRealGdp2010Prices(from = from, to = to)

        // then
        assertThat(result.size).isEqualTo(4)

        val first = result.first()
        assertThat(first.date).isEqualTo(LocalDate.of(2020, 3, 31))
        assertThat(first.gdpBillionsCrowns).isEqualTo(1182.352)
        assertThat(first.isValid).isTrue()

        val last = result.last()
        assertThat(last.date).isEqualTo(LocalDate.of(2019, 6, 30))
        assertThat(last.gdpBillionsCrowns).isEqualTo(1212.254)
        assertThat(last.isValid).isTrue()
    }


}