package com.estn.economy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.*
import javax.annotation.PostConstruct




fun main(args: Array<String>) {
    runApplication<EconomyApplication>(*args)
}

@ConfigurationPropertiesScan
@EnableScheduling
@SpringBootApplication
class EconomyApplication {

    @PostConstruct
    fun init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }
}
