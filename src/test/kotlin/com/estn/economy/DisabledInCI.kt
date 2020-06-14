package com.estn.economy

import org.springframework.test.context.junit.jupiter.DisabledIf

@DisabledIf(expression = "#{systemProperties['CI'].equals('true')}")
@Retention(AnnotationRetention.RUNTIME)
annotation class DisabledInCI