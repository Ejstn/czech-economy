package com.estn.economy

import com.estn.economy.exchangerate.CNBClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener

fun main(args: Array<String>) {
    runApplication<EconomyApplication>(*args)

}

@SpringBootApplication
class EconomyApplication {

	@Autowired
	lateinit var client: CNBClient

    @EventListener(value = [ApplicationReadyEvent::class])
    fun fetchData() {
        client.fetchExchangeRate()
    }
}

