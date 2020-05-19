package com.estn.economy.unemploymentrate.presentation

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/nezamestnanost")
class UnemploymentController {

    val template = "pages/unemployment"

    @GetMapping
    fun getUnemployment(model: Model) : String {
        return template
    }


}