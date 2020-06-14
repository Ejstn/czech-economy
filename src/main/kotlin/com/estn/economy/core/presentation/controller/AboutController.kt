package com.estn.economy.core.presentation.controller

import com.estn.economy.core.domain.FetchDataSourcesUseCase
import com.estn.economy.core.presentation.model.About
import com.estn.economy.core.presentation.model.Home
import com.estn.economy.core.presentation.model.Routing
import com.estn.economy.core.presentation.utility.addBreadcrumbs
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(Routing.ABOUT)
class AboutController(private val fetchDataSources: FetchDataSourcesUseCase) {

    @GetMapping
    fun getDataSources(model: Model): String {
        model.addBreadcrumbs(Home, About)

        model.addAttribute("dataSources", fetchDataSources.execute())

        return "pages/about"
    }

}