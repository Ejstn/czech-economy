package com.estn.economy.nationalbudget.presentation

import com.estn.economy.core.presentation.model.Home
import com.estn.economy.core.presentation.model.NationalBudget
import com.estn.economy.core.presentation.model.Routing
import com.estn.economy.core.presentation.utility.addBreadcrumbs
import com.estn.economy.core.presentation.utility.mapToPairs
import com.estn.economy.nationalbudget.domain.FetchNationalBudgetUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(Routing.NATIONAL_BUDGET)
class NationalBudgetController(private val fetchBudget: FetchNationalBudgetUseCase) {

    val template = "pages/national_budget"

    @GetMapping
    fun getGdp(model: Model): String {
        model.addBreadcrumbs(Home, NationalBudget)

        val publicDebt = fetchBudget.fetchPublicDebt().mapToPairs()
        model.addAttribute("publicDebtGraph", publicDebt)
        model.addAttribute("currentDebt", publicDebt.last())
        model.addAttribute("highestDebt", publicDebt.maxBy { it.second as Comparable<Any> })
        model.addAttribute("lowestDebt", publicDebt.minBy { it.second as Comparable<Any> })

        val budgetBalance = fetchBudget.fetchBudgetBalance().mapToPairs()
        model.addAttribute("budgetBalanceGraph", budgetBalance)
        model.addAttribute("currentBalance", budgetBalance.last())
        model.addAttribute("highestBalance", budgetBalance.maxBy { it.second as Comparable<Any> })
        model.addAttribute("lowestBalance", budgetBalance.minBy { it.second as Comparable<Any> })

        return template
    }

}