package com.estn.economy.exchangerate.api

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
                    .status(fetchedEntity.statusCode.name)
                    .withDetail("request time", "${requestTime}ms")
                    .build()

        } catch (e: Exception) {
            Health.down(e).build()
        }

    }
}