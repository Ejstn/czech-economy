package com.estn.economy.exchangerate.presentation

import com.estn.economy.core.presentation.BreadcrumbItem
import com.estn.economy.core.presentation.ExchangeRates
import com.estn.economy.core.presentation.Home
import com.estn.economy.core.presentation.utility.addBreadcrumbs
import com.estn.economy.exchangerate.domain.ExchangeRate
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
    fun getExchangeRates(model: Model) : String {
        model.addBreadcrumbs(Home, ExchangeRates)

        val today = LocalDate.now()
        val monthAgo = today.minusMonths(1)

        val result = fetchExchange.fetchExchangeRateBetweenDates("USD", monthAgo, today)

        val currentUSD = result.last()

        val previousUSD = result.first()

        val USD = MainRateOverview(name =  previousUSD.currencyName.capitalize(),
                currentValue = currentUSD,
                comparedValue = previousUSD,
                currentVsComparedPercentChange = currentUSD.exchangeRate / previousUSD.exchangeRate * 100,
                development = result
        )

        model.addAttribute("usd", USD)

        return "pages/exchangerate"
    }

    data class MainRateOverview(val name: String,
                                val currentValue : ExchangeRate,
                                val comparedValue : ExchangeRate,
                                val currentVsComparedPercentChange: Double,
                                val development: Collection<ExchangeRate>)


    @GetMapping("/{currencyCode}")
    fun getChartsForGivenCurrency(@PathVariable currencyCode: String, model: Model): String {
        val ratesList = fetchExchange.fetchByCurrencyOrderByDate(currencyCode)

        if (ratesList.isEmpty()) {
            throw NoSuchElementException()
        }

        val resolvedCurrencyCode = ratesList.first().currencyCode
        val currencyAmount = ratesList.first().amount

        model.addAttribute("rates", ratesList)
        model.addAttribute("currencyCode", resolvedCurrencyCode)
        model.addAttribute("currencyAmount", currencyAmount)

        model.addBreadcrumbs(Home, ExchangeRates, BreadcrumbItem(currencyCode))

        return "chart"
    }

}