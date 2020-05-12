package com.estn.economy.publicdebt.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PublicDebtRepository : JpaRepository<PublicDebtEntity, Int> {
}