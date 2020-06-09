package com.estn.economy.grossdomesticproduct.presentation

import com.estn.economy.core.domain.OutputPercentageData
import com.estn.economy.core.presentation.formatting.Percentage
import com.estn.economy.core.presentation.formatting.QuarterAndYear
import com.estn.economy.core.presentation.model.Gdp
import com.estn.economy.core.presentation.model.Home
import com.estn.economy.core.presentation.model.Routing
import com.estn.economy.core.presentation.utility.addBreadcrumbs
import com.estn.economy.core.presentation.utility.mapToPairs
import com.estn.economy.core.presentation.utility.quarterToRoman
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductEntity
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
        model.addAttribute("realGdpChanges", gdpChanges.mapToPairs {
            Pair("${it.dataPoint.quarter.quarterToRoman()} ${it.dataPoint.year}", it.value)
        })

        val current = gdpChanges.last()
        model.addTriple("current", "Aktuální HDP", current)

        val lowest = gdpChanges.minBy { it.value } ?: throw IllegalStateException("lowest cannot be null but it is !")
        model.addTriple("lowest", "Největší propad HDP", lowest)

        val highest = gdpChanges.maxBy { it.value } ?: throw IllegalStateException("highest cannot be null but it is!")
        model.addTriple("highest", "Nejvyšší růst HDP", highest)

        return template
    }

    private fun Model.addTriple(attributeName: String,
                                title: String,
                                data: OutputPercentageData<GrossDomesticProductEntity>) {
        this.addAttribute(attributeName,
                Triple(
                        title,
                        QuarterAndYear(data.dataPoint.quarter, data.dataPoint.year),
                        Percentage(data.value)))
    }


}