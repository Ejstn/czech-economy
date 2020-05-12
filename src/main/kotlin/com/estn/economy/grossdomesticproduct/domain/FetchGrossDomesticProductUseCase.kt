package com.estn.economy.grossdomesticproduct.domain

import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductRepository
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType
import org.springframework.stereotype.Service


@Service
class FetchGrossDomesticProductUseCase (private val repository: GrossDomesticProductRepository) {

    fun fetchGdp() = repository
            .getAllByTypeEqualsOrderByYearAsc(GrossDomesticProductType.NOMINAL)

}