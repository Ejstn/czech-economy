package com.estn.economy.inflation.presentation

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/inflace")
class InflationController {

    val template = "pages/inflation"

    @GetMapping
    fun getInflation(model: Model) : String {
        return template
    }


}