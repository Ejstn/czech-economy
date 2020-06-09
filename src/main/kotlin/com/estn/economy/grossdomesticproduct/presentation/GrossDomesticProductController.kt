package com.estn.economy.grossdomesticproduct.presentation

import com.estn.economy.core.presentation.model.Gdp
import com.estn.economy.core.presentation.model.Home
import com.estn.economy.core.presentation.model.Routing
import com.estn.economy.core.presentation.utility.addBreadcrumbs
import com.estn.economy.core.presentation.utility.mapToPairs
import com.estn.economy.core.presentation.utility.quarterToRoman
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType.REAL_2010_PRICES
import com.estn.economy.grossdomesticproduct.domain.FetchGrossDomesticProductUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(Routing.GDP)
class GrossDomesticProductController(private val fetchGdpUseCase: FetchGrossDomesticProductUseCase) {

    val template = "pages/gdp"

    @GetMapping
    fun getGdp(model: Model): String {
        model.addBreadcrumbs(Home, Gdp)

        model.addAttribute("realGdp2010Prices", fetchGdpUseCase.fetchGdp(REAL_2010_PRICES)
                .mapToPairs { Pair("${it.quarter.quarterToRoman()} ${it.year}", it.gdpMillionsCrowns) })

        val gdpChanges = fetchGdpUseCase.fetchPercentChangesPerQuarter(REAL_2010_PRICES)
                .mapToPairs {
                    Pair("${it.dataPoint.quarter.quarterToRoman()} ${it.dataPoint.year}", it.value)
                }

        model.addAttribute("current", gdpChanges.last())
        model.addAttribute("lowest", gdpChanges.minBy { it.second as Comparable<Any> })
        model.addAttribute("highest", gdpChanges.maxBy { it.second as Comparable<Any> })

        model.addAttribute("realGdpChanges", gdpChanges)


        return template
    }


}