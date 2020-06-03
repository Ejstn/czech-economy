package com.estn.economy.unemploymentrate.presentation

import com.estn.economy.core.presentation.date.translate
import com.estn.economy.core.presentation.model.Home
import com.estn.economy.core.presentation.model.Routing
import com.estn.economy.core.presentation.model.Unemployment
import com.estn.economy.core.presentation.utility.addBreadcrumbs
import com.estn.economy.core.presentation.utility.mapToPairs
import com.estn.economy.unemploymentrate.domain.FetchUnemploymentRateUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.time.Month

@Controller
@RequestMapping(Routing.UNEMPLOYMENT)
class UnemploymentController(private val fetchUnemp: FetchUnemploymentRateUseCase) {

    val template = "pages/unemployment"

    @GetMapping
    fun getUnemployment(model: Model): String {
        model.addBreadcrumbs(Home, Unemployment)

        val unemployment = fetchUnemp.fetchAllQuarteryUnempRates()
                .mapToPairs { Pair("${Month.of(it.month).translate()} ${it.year}", it.unemploymentRatePercent) }

        model.addAttribute("highest", unemployment.maxBy { it.second as Comparable<Any> })
        model.addAttribute("lowest", unemployment.minBy { it.second as Comparable<Any> })
        model.addAttribute("current", unemployment.last())
        model.addAttribute("unempGraph", unemployment)

        return template
    }


}