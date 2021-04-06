package com.estn.economy.unemploymentrate.presentation

import com.estn.economy.core.presentation.formatting.MonthAndYear
import com.estn.economy.core.presentation.formatting.MonthAndYearConverter
import com.estn.economy.core.presentation.formatting.percentage
import com.estn.economy.core.presentation.model.Home
import com.estn.economy.core.presentation.model.OverviewAndGraph
import com.estn.economy.core.presentation.model.Routing
import com.estn.economy.core.presentation.model.Unemployment
import com.estn.economy.core.presentation.utility.addBreadcrumbs
import com.estn.economy.core.presentation.utility.mapToPairs
import com.estn.economy.unemploymentrate.data.database.UnemploymentRateEntity
import com.estn.economy.unemploymentrate.domain.FetchUnemploymentRateUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(Routing.UNEMPLOYMENT)
class UnemploymentController(private val fetchUnemp: FetchUnemploymentRateUseCase,
                             private val monthAndYearConverter: MonthAndYearConverter) {

    val template = "pages/unemployment"

    @GetMapping
    fun getUnemployment(model: Model): String {
        model.addBreadcrumbs(Home, Unemployment)

        val unemployment = fetchUnemp.fetchAllMonthlyUnempRates()

        model.addAttribute("unemployment", OverviewAndGraph(
                graph = unemployment.mapToPairs {
                    Pair(monthAndYearConverter.convert(MonthAndYear(it.month, it.year)),
                            it.unemploymentRatePercent)
                },
                overview = overview(unemployment)
        ))

        return template
    }

    private fun overview(input: List<UnemploymentRateEntity>): List<Triple<*, *, *>> {
        return listOf(
                triple("Aktuální", input.last()),
                triple("Nejnižší", input.minByOrNull { it.unemploymentRatePercent }!!),
                triple("Nejvyšší", input.maxByOrNull { it.unemploymentRatePercent }!!)
        )
    }

    private fun triple(title: String, unemployment: UnemploymentRateEntity): Triple<*, *, *> {
        return Triple(title,
                MonthAndYear(unemployment.month, unemployment.year),
                unemployment.unemploymentRatePercent.percentage
        )
    }


}