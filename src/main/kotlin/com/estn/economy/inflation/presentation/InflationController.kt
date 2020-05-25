package com.estn.economy.inflation.presentation

import com.estn.economy.core.domain.date.translate
import com.estn.economy.core.presentation.Home
import com.estn.economy.core.presentation.Inflation
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
@RequestMapping("/inflace")
class InflationController(private val fetchInflation: FetchInflationRateUseCase) {

    val template = "pages/inflation"

    @GetMapping
    fun getInflation(model: Model): String {

        model.addBreadcrumbs(Home, Inflation)

        model.addAttribute("monthlyInflation", fetchInflation.fetchAllInflationRatesByType(InflationType.THIS_MONTH_VS_PREVIOUS_MONTH)
                .mapToPairs {
                    Pair("${Month.of(it.month).translate()} ${it.year} ", it.valuePercent)
                })

        model.addAttribute("yearlyInflation", fetchInflation.fetchAllYearlyInflationRates()
                .mapToPairs())


        return template
    }


}