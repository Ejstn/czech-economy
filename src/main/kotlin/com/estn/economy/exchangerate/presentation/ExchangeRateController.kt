package com.estn.economy.exchangerate.presentation

import com.estn.economy.core.presentation.BreadcrumbItem
import com.estn.economy.core.presentation.ExchangeRates
import com.estn.economy.core.presentation.Home
import com.estn.economy.core.presentation.utility.addBreadcrumbs
import com.estn.economy.exchangerate.domain.FetchExchangeRateUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.time.LocalDate

@Controller
@RequestMapping("/kurzy")
class ExchangeRateController(private val fetchExchange: FetchExchangeRateUseCase) {


    @GetMapping
    fun getExchangeRates(model: Model): String {
        model.addBreadcrumbs(Home, ExchangeRates)

        val rates = fetchExchange.fetchLatestRates()

        model.addAttribute("rates", rates)
        model.addAttribute("date", rates.first().date)

        return "pages/exchangerate"
    }

    @GetMapping("/{currencyCode}")
    fun getChartsForGivenCurrency(@PathVariable currencyCode: String, model: Model): String {

        model.addBreadcrumbs(Home, ExchangeRates, BreadcrumbItem(currencyCode))

        val ratesList = fetchExchange.fetchByCurrencyOrderByDate(currencyCode)

        if (ratesList.isEmpty()) {
            throw NoSuchElementException()
        }

        model.addAttribute("rates", ratesList)
        model.addAttribute("currencyCode", ratesList.first().currencyCode)
        model.addAttribute("currencyAmount", ratesList.first().amount)
        model.addAttribute("current", ratesList.last())
        model.addAttribute("lowest", ratesList.minBy { it.exchangeRate })
        model.addAttribute("highest", ratesList.maxBy { it.exchangeRate })

        model.addAttribute("monthAgo", ratesList.first { it.date.isAfter(LocalDate.now().minusMonths(1)) })
        model.addAttribute("halfYearAgo", ratesList.first { it.date.isAfter(LocalDate.now().minusMonths(6)) })
        model.addAttribute("yearAgo", ratesList.first { it.date.isAfter(LocalDate.now().minusYears(1)) })

        return "pages/exchangerate_detail"
    }

}