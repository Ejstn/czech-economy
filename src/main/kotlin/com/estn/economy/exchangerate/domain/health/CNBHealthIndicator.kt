package com.estn.economy.exchangerate.domain.health

import com.estn.economy.exchangerate.data.cnbapi.CNBClient
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component

/**
 * Written by ctn.
 */
@Component
class CNBHealthIndicator(private val client: CNBClient) : HealthIndicator {

    override fun health(): Health {
        return try {
            val timestamp = System.currentTimeMillis()
            val fetchedEntity = client.fetchLatestExchangeRatesEntity()

            val requestTime = System.currentTimeMillis() - timestamp

            val health: Health.Builder = if (fetchedEntity.statusCode.is2xxSuccessful) {
                Health.up()
            } else {
                Health.down()
            }

            health
                    .withDetail("request time", "${requestTime}ms")
                    .withDetail("response http code", fetchedEntity.statusCode.value())
                    .build()

        } catch (e: Exception) {
            Health.down(e).build()
        }

    }
}