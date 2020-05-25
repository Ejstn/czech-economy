package com.estn.economy.core.presentation

sealed class BreadcrumbItem(val name: String,
                            val link: String = "/")

data class Breadcrumbs(val items: Collection<BreadcrumbItem>)


object Home : BreadcrumbItem("Domů")

object Gdp : BreadcrumbItem("HDP", "/hdp")