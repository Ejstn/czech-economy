package com.estn.economy.grossdomesticproduct.presentation

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/hdp")
class GrossDomesticProductController {

    val template = "pages/gdp"

    @GetMapping
    fun getGdp(model: Model) : String {
        return template
    }


}