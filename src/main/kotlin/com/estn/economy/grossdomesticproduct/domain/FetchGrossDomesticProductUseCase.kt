package com.estn.economy.grossdomesticproduct.domain

import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductRepository
import org.springframework.stereotype.Service


@Service
class FetchGrossDomesticProductUseCase (private val repository: GrossDomesticProductRepository) {

    fun fetchYearyGdps() = repository.getAllGdpsSummedByYear()


}