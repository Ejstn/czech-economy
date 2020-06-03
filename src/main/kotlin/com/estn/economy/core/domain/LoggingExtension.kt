package com.estn.economy.core.domain

import org.slf4j.LoggerFactory

fun getLogger(forClass: Class<*>): org.slf4j.Logger =
        LoggerFactory.getLogger(forClass)