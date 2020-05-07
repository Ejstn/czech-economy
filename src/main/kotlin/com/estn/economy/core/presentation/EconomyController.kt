package com.estn.economy.core.presentation

import com.estn.economy.core.domain.date.DateFormatter
import com.estn.economy.core.domain.date.translate
import com.estn.economy.exchangerate.domain.ExchangeRate
import com.estn.economy.exchangerate.domain.FetchExchangeRateUseCase
import com.estn.economy.grossdomesticproduct.domain.FetchGrossDomesticProductUseCase
import com.estn.economy.grossdomesticproduct.domain.GrossDomesticProductPerYear
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.format.TextStyle
import java.util.*
import kotlin.NoSuchElementException

/**
 * Written by Martin Soukup on 14.1.2020.
 */
@Controller
class EconomyController(private val fetchExchangeUseCase: FetchExchangeRateUseCase,
                        private val fetchGdpUseCase: FetchGrossDomesticProductUseCase,
                        private val dateFormatter: DateFormatter) {

    @GetMapping
    fun getDashboard(model: Model): String {
        val exchangeRates = fetchExchangeUseCase.fetchLatestRates()
        val date = exchangeRates.first().date

        val formattedDate = " ${date.dayOfWeek.translate(false)} ${dateFormatter.formatDateForFrontEnd(date)}"
        val yearlyGDPs = fetchGdpUseCase.fetchYearyGdps()

        val dashboard = EconomyDashboard(
                exchangeRatesDate = formattedDate,
                exchangeRates = exchangeRates,
                yearlyGDPs = yearlyGDPs)

        model.addAttribute("dashboard", dashboard)
        return "index"
    }

    data class EconomyDashboard(val exchangeRatesDate: String,
                                val exchangeRates: Collection<ExchangeRate>,
                                val yearlyGDPs: Collection<GrossDomesticProductPerYear>)

    @GetMapping("/kurzy/{currencyCode}")
    fun getChartsForGivenCurrency(@PathVariable currencyCode: String, model: Model): String {
        val ratesList = fetchExchangeUseCase.fetchByCurrencyOrderByDate(currencyCode)

        if (ratesList.isEmpty()) {
            throw NoSuchElementException()
        }

        val resolvedCurrencyCode = ratesList.first().currencyCode
        val currencyAmount = ratesList.first().amount

        model.addAttribute("rates", ratesList)
        model.addAttribute("currencyCode", resolvedCurrencyCode)
        model.addAttribute("currencyAmount", currencyAmount)

        return "chart"
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [NoSuchElementException::class])
    fun handle404() = "error/4xx"

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [Exception::class])
    fun handle500() = "error/5xx"

}
