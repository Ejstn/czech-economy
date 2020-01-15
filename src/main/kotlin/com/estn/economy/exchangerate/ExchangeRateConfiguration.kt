package com.estn.economy.exchangerate

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Max
import javax.validation.constraints.Min

/**
 * Written by ctn.
 */
@Validated
@ConfigurationProperties(prefix = "exchangerate")
class ExchangeRateConfiguration {

    @Min(1)
    @Max(50000)
    var batchSyncSize: Int = 1

}