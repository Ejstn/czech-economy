package com.estn.economy.utility

import com.estn.economy.core.presentation.BreadcrumbItem
import com.estn.economy.core.presentation.Breadcrumbs
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model

fun breadcrumbs(vararg items: BreadcrumbItem): ResultMatcher {
    return model().attribute("breadcrumbs", Breadcrumbs(items.toList()))
}