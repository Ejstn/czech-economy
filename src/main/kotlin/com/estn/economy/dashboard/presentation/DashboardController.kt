package com.estn.economy.dashboard.presentation

import com.estn.economy.dashboard.domain.ComposeDashboardUseCase
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.NoSuchElementException

/**
 * Written by Martin Soukup on 14.1.2020.
 */
@Controller
class DashboardController(private val composeDashboard: ComposeDashboardUseCase) {

    @GetMapping
    fun getDashboard(model: Model): String {
        val dashboard = composeDashboard.execute()

        model.addAttribute("dashboard", dashboard)
        return "index"
    }


}
