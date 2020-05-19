package com.estn.economy.nationalbudget.presentation

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/statni-rozpocet")
class NationalBudgetController {

    val template = "pages/national_budget"

    @GetMapping
    fun getGdp(model: Model) : String {
        return template
    }

}