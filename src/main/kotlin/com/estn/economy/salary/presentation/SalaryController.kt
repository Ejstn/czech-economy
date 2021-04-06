package com.estn.economy.salary.presentation

import com.estn.economy.core.presentation.formatting.QuarterAndYear
import com.estn.economy.core.presentation.formatting.QuarterAndYearConverter
import com.estn.economy.core.presentation.formatting.czechCrowns
import com.estn.economy.core.presentation.model.Home
import com.estn.economy.core.presentation.model.OverviewAndGraph
import com.estn.economy.core.presentation.model.Routing
import com.estn.economy.core.presentation.model.Salary
import com.estn.economy.core.presentation.utility.addBreadcrumbs
import com.estn.economy.core.presentation.utility.mapToPairs
import com.estn.economy.salary.data.database.SalaryEntity
import com.estn.economy.salary.domain.FetchSalaryUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(Routing.SALARY)
class SalaryController(private val fetchSalary: FetchSalaryUseCase,
                       private val quarterAndYearConverter: QuarterAndYearConverter) {

    val template = "pages/salary"

    @GetMapping
    fun getSalary(model: Model): String {
        model.addBreadcrumbs(Home, Salary)

        val salary = fetchSalary.fetchAll()

        model.addAttribute("salary", OverviewAndGraph(
                graph = salary.mapToPairs {
                    Pair(quarterAndYearConverter.convert(QuarterAndYear(it.quarter, it.year)),
                            it.salaryCrowns)
                },
                overview = overview(salary)
        ))

        return template
    }

    private fun overview(input: List<SalaryEntity>): List<Triple<*, *, *>> {
        return listOf(
                triple("Aktuální", input.last()),
                triple("Nejnižší", input.minByOrNull { it.salaryCrowns }!!),
                triple("Nejvyšší", input.maxByOrNull { it.salaryCrowns }!!)
        )
    }

    private fun triple(title: String, salary: SalaryEntity): Triple<*, *, *> {
        return Triple(title,
                QuarterAndYear(salary.quarter, salary.year),
                salary.salaryCrowns.czechCrowns
        )
    }

}