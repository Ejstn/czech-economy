package com.estn.economy.core.presentation

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/oaplikaci")
class AboutController {


    @GetMapping("/zdroje-dat")
    fun getDataSources(model: Model) : String {
        return "data_sources"
    }


}