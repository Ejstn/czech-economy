package com.estn.economy.inflation.presentation

import com.estn.economy.core.presentation.formatting.MonthAndYear
import com.estn.economy.core.presentation.formatting.MonthAndYearConverter
import com.estn.economy.core.presentation.formatting.Percentage
import com.estn.economy.core.presentation.formatting.percentage
import com.estn.economy.core.presentation.model.Home
import com.estn.economy.core.presentation.model.Inflation
import com.estn.economy.core.presentation.model.Routing
import com.estn.economy.core.presentation.utility.addBreadcrumbs
import com.estn.economy.core.presentation.utility.mapToPairs
import com.estn.economy.inflation.data.InflationRateEntity
import com.estn.economy.inflation.data.InflationType
import com.estn.economy.inflation.data.InflationType.*
import com.estn.economy.inflation.domain.FetchInflationRateUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(Routing.INFLATION)
class InflationRateController(private val fetchInflation: FetchInflationRateUseCase,
                              private val monthAndYear: MonthAndYearConverter) {

    val template = "pages/inflation"

    @GetMapping
    fun getInflation(model: Model): String {
        model.addBreadcrumbs(Home, Inflation)

        model.addSection("averageYearlyInflation", THIS_YEAR_VS_LAST_YEAR)
        model.addSection("thisMonthVsPrevYearsMonthsInflation", THIS_MONTH_VS_PREVIOUS_YEARS_MONTH)
        model.addSection("monthlyInflation", THIS_MONTH_VS_PREVIOUS_MONTH)

        return template
    }

    private fun Model.addSection(attributeName: String, type: InflationType) {
        val inflation = fetchInflation.fetchAllInflationRatesByType(type)
        this.addAttribute(attributeName,
                OverviewAndGraph(overview(inflation), formatGraphItems(inflation)))
    }

    private fun formatGraphItems(input: List<InflationRateEntity>): List<Pair<Any, Any>> {
        return input.mapToPairs {
            Pair(monthAndYear.convert(MonthAndYear(it.month, it.year)), it.valuePercent)
        }
    }

    private fun overview(input: List<InflationRateEntity>): List<Triple<*, *, *>> {
        return listOf(
                triple("Aktuální", input.last()),
                triple("Nejnižší", input.minBy { it.valuePercent }!!),
                triple("Nejvyšší", input.maxBy { it.valuePercent }!!)
        )
    }

    private fun triple(title: String, highestMonthly: InflationRateEntity): Triple<String, MonthAndYear, Percentage> {
        return Triple(title,
                MonthAndYear(highestMonthly.month, highestMonthly.year),
                highestMonthly.valuePercent.percentage
        )
    }

    data class OverviewAndGraph(val overview: List<Triple<*, *, *>>,
                                val graph: List<Pair<*, *>>)
}