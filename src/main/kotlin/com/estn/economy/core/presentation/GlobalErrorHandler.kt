package com.estn.economy.core.presentation

import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalErrorHandler {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [NoSuchElementException::class])
    fun handle404(model: Model): String {
        model["status"] = HttpStatus.NOT_FOUND.value()
        return "error/4xx"
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [Exception::class])
    fun handle500(model: Model) : String {
        model["status"] = HttpStatus.INTERNAL_SERVER_ERROR.value()
        return "error/5xx"
    }

}