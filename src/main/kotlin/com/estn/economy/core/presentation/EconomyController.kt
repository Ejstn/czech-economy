package com.estn.economy.core.presentation

import com.estn.economy.core.domain.date.DateFormatter
import com.estn.economy.exchangerate.domain.ExchangeRate
import com.estn.economy.exchangerate.domain.ExchangeRateService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

/**
 * Written by Martin Soukup on 14.1.2020.
 */
@Controller
class EconomyController(private val exchangeService: ExchangeRateService,
                        private val dateFormatter: DateFormatter) {

    @GetMapping
    fun getDashboard(model: Model): String {
        val exchangeRates = exchangeService.fetchLatestExchangeRates()
        val formattedDate = dateFormatter.formatDateForFrontEnd(exchangeRates.first().date)

        val dashboard = EconomyDashboard(
                exchangeRatesDate = formattedDate,
                exchangeRates = exchangeRates)

        model.addAttribute("dashboard", dashboard)
        return "index"
    }

    data class EconomyDashboard(val exchangeRatesDate: String,
                                val exchangeRates: Collection<ExchangeRate>)

}
