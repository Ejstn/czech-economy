package com.estn.economy.inflation.presentation

import com.estn.economy.core.presentation.Home
import com.estn.economy.core.presentation.Inflation
import com.estn.economy.core.presentation.utility.addBreadcrumbs
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/inflace")
class InflationController {

    val template = "pages/inflation"

    @GetMapping
    fun getInflation(model: Model): String {


        model.addBreadcrumbs(Home, Inflation)

        return template
    }


}