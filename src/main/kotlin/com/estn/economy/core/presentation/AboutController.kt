package com.estn.economy.core.presentation

import com.estn.economy.core.presentation.utility.addBreadcrumbs
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(Routing.ABOUT)
class AboutController {

    @GetMapping
    fun getDataSources(model: Model): String {
        model.addBreadcrumbs(Home, About)

        return "about"
    }

}