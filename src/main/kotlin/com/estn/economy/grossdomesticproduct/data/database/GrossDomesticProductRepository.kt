package com.estn.economy.grossdomesticproduct.data.database

import com.estn.economy.grossdomesticproduct.domain.GrossDomesticProductPerYear
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface GrossDomesticProductRepository : JpaRepository<GrossDomesticProductEntity, GrossDomesticProductKey> {


    @Query("select new com.estn.economy.grossdomesticproduct.domain.GrossDomesticProductPerYear" +
            "(year, sum(gdpMillionsCrowns)) from gross_domestic_product group by year order by year asc")
    fun getAllGdpsSummedByYear() : List<GrossDomesticProductPerYear>

}