package com.estn.economy.core.presentation

open class BreadcrumbItem(val name: String,
                            val link: String = "/")

data class Breadcrumbs(val items: Collection<BreadcrumbItem>)


object Home : BreadcrumbItem("Domů")

object Gdp : BreadcrumbItem("HDP", "/hdp")

object Inflation : BreadcrumbItem("Inflace", "/inflace")

object ExchangeRates : BreadcrumbItem("Kurzy", "/kurzy")