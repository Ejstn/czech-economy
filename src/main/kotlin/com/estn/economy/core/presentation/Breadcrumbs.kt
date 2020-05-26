package com.estn.economy.core.presentation

open class BreadcrumbItem(val name: String,
                          val link: String = Routing.DASHBOARD)

data class Breadcrumbs(val items: Collection<BreadcrumbItem>)

object Home : BreadcrumbItem("Domů")

object Gdp : BreadcrumbItem("HDP", Routing.GDP)

object Inflation : BreadcrumbItem("Inflace", Routing.INFLATION)

object ExchangeRates : BreadcrumbItem("Kurzy", Routing.EXCHANGE_RATE)