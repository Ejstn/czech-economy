package com.estn.economy.inflation.presentation

import com.estn.economy.core.presentation.date.translate
import com.estn.economy.core.presentation.model.Home
import com.estn.economy.core.presentation.model.Inflation
import com.estn.economy.core.presentation.model.Routing
import com.estn.economy.core.presentation.utility.addBreadcrumbs
import com.estn.economy.core.presentation.utility.mapToPairs
import com.estn.economy.inflation.data.InflationType
import com.estn.economy.inflation.domain.FetchInflationRateUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.time.Month

@Controller
@RequestMapping(Routing.INFLATION)
class InflationController(private val fetchInflation: FetchInflationRateUseCase) {

    val template = "pages/inflation"

    @GetMapping
    fun getInflation(model: Model): String {

        model.addBreadcrumbs(Home, Inflation)

        val yearlyInflation = fetchInflation.fetchAllYearlyInflationRates()
                .mapToPairs()

        model.addAttribute("yearlyInflation", yearlyInflation)

        model.addAttribute("current", yearlyInflation.last())
        model.addAttribute("highest", yearlyInflation.maxBy { it.second as Comparable<Any> })
        model.addAttribute("lowest", yearlyInflation.minBy { it.second as Comparable<Any> })

        model.addAttribute("monthlyInflation", fetchInflation.fetchAllInflationRatesByType(InflationType.THIS_MONTH_VS_PREVIOUS_MONTH)
                .mapToPairs {
                    Pair("${Month.of(it.month).translate()} ${it.year} ", it.valuePercent)
                })

        return template
    }


}