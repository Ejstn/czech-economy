package com.estn.economy

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
    fun getExchangeRates(model: Model): String {
        val exchangeRates = exchangeService.fetchExchangeRates()
        model.addAttribute("exchangerates", exchangeRates)

        val formattedDate = dateFormatter.formatDate(exchangeRates.first().date)
        model.addAttribute("date", formattedDate)

        return "index"
    }

}

@RestController
@RequestMapping("/api")
class EconomyRestController(private val exchangeService: ExchangeRateService) {

    @GetMapping
    fun getExchangeRatesApi() = exchangeService.fetchExchangeRates()

}