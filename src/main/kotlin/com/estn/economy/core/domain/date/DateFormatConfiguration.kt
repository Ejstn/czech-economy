package com.estn.economy.core.domain.date

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

/**
 * Written by ctn.
 */
@Validated
@ConfigurationProperties(prefix = "date.format")
class DateFormatConfiguration {

    @NotEmpty
    @NotBlank
    var cnbExchangeRateApiDateFormat: String = "dd.MM.yyyy"

}