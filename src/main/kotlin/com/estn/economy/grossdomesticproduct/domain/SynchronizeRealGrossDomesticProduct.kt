package com.estn.economy.grossdomesticproduct.domain

import com.estn.economy.core.data.api.CNBClient
import com.estn.economy.grossdomesticproduct.data.api.toEntity
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductRepository
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SynchronizeRealGrossDomesticProduct(private val api: CNBClient,
                                          private val repository: GrossDomesticProductRepository) {

    val startingDate = LocalDate.of(1996, 1, 1)
    val gdpType = GrossDomesticProductType.REAL_2010_PRICES

    fun execute() {
        api.fetchQuarterlyRealGdp2010Prices(startingDate)
                .map { it.toEntity(gdpType) }
                .toList()
                .also { repository.saveAll(it) }
    }

}