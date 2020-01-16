package com.estn.economy

import com.estn.economy.core.DateFormatter
import com.estn.economy.exchangerate.ExchangeRateService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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

}

@RestController
@RequestMapping("/api")
class EconomyRestController(private val exchangeService: ExchangeRateService) {

    @GetMapping("/exchangerate")
    fun getExchangeRatesApi(): ExchangeRatesResponse {
        return ExchangeRatesResponse(exchangeService.fetchLatestExchangeRates())
    }

}