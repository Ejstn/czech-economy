package com.estn.economy.grossdomesticproduct.data.database

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GrossDomesticProductRepository : JpaRepository<GrossDomesticProductEntity, GrossDomesticProductKey> {

    fun getAllByTypeEqualsOrderByYearAsc(type: GrossDomesticProductType) : List<GrossDomesticProductEntity>

}