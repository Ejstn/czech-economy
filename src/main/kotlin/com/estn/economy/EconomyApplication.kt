package com.estn.economy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

fun main(args: Array<String>) {
    runApplication<EconomyApplication>(*args)
}

@ConfigurationPropertiesScan
@EnableScheduling
@SpringBootApplication
class EconomyApplication
