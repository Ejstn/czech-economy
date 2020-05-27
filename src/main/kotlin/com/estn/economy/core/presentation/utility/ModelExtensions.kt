package com.estn.economy.core.presentation.utility

import com.estn.economy.core.presentation.model.BreadcrumbItem
import com.estn.economy.core.presentation.model.Breadcrumbs
import org.springframework.ui.Model

fun Model.addBreadcrumbs(vararg items: BreadcrumbItem) {
    this.addAttribute("breadcrumbs", Breadcrumbs(items.toList()))
}