package com.estn.economy.utility

import org.mockito.Mockito

/**
 * Written by estn on 17.01.2020.
 */
fun <T> any(type: Class<T>): T = Mockito.any<T>(type)