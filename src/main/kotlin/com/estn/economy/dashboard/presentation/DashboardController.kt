package com.estn.economy.dashboard.presentation

import com.estn.economy.core.presentation.model.Routing
import com.estn.economy.dashboard.domain.ComposeDashboardUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

/**
 * Written by Martin Soukup on 14.1.2020.
 */
@Controller(Routing.DASHBOARD)
class DashboardController(private val composeDashboard: ComposeDashboardUseCase) {

    @GetMapping
    fun getDashboard(model: Model): String {
        model.addAttribute("dashboard", composeDashboard.execute())

        return "pages/dashboard"
    }


}
