package com.estn.economy.core.domain

import org.springframework.stereotype.Service

@Service
class FetchDataSourcesUseCase {

    fun execute() : List<DataSource> {
        return listOf(
                RealGdp,
                Inflation,
                Unemployment,
                PublicDebt,
                BudgetBalance,
                Salary,
                ExchangeRates
        )
    }

}

val cnb = "ČNB"
val csu = "ČSÚ"

open class DataSource(val dataPointName: String,
                      val sourceName: String,
                      val url: String)

object RealGdp : DataSource(
        "reálný HDP",
        cnb,
        "https://www.cnb.cz/cnb/STAT.ARADY_PKG.PARAMETRY_SESTAVY?p_sestuid=29930&p_strid=ACL&p_lang=CS")

object Inflation : DataSource(
        "inflace",
        cnb,
        "https://www.czso.cz/csu/czso/mira_inflace")

object Unemployment : DataSource(
        "nezaměstnanost",
        csu,
        "https://www.cnb.cz/cnb/STAT.ARADY_PKG.PARAMETRY_SESTAVY?p_sestuid=21751&p_strid=ACHAB&p_lang=CS"
)

object PublicDebt : DataSource(
        "státní dluh",
        csu,
        "https://www.czso.cz/documents/10180/123502863/chmucr040120.xlsx/d8083bc3-fe32-47ff-a389-8e156439d320?version=1.1"
)

object BudgetBalance : DataSource(
        "saldo rozpočtu",
        csu,
        "https://www.czso.cz/documents/10180/123502863/chmucr040120.xlsx/d8083bc3-fe32-47ff-a389-8e156439d320?version=1.1"
)

object Salary: DataSource (
        "nominální průmerné mzdy",
        cnb,
        "https://www.cnb.cz/cnb/STAT.ARADY_PKG.PARAMETRY_SESTAVY?p_sestuid=21737&p_strid=ACFA&p_lang=CS"
)

object ExchangeRates: DataSource (
        "kurzy koruny",
        cnb,
        "https://www.cnb.cz/cs/financni_trhy/devizovy_trh/kurzy_devizoveho_trhu/denni_kurz.xml"
)
