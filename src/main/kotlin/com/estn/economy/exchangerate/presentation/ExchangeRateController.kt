package com.estn.economy.exchangerate.presentation

import com.estn.economy.exchangerate.domain.FetchExchangeRateUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class ExchangeRateController(private val fetchExchangeUseCase: FetchExchangeRateUseCase) {


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

}